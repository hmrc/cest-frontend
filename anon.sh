#!/bin/sh

BFG=/usr/local/bin/bfg.jar
REPO=${PWD##*/}
BRANCHES=`git branch | wc -l`

RED='\033[0;31m'
NC='\033[0m'
GREEN='\033[92m'

function redactionOrchestrator() {
    initialisation
    redactCommits
    updateRemotes
    cleanRepo

    echo "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓"
    echo "┃ ${GREEN}Success:${NC}                                                                            ┃"
    echo "┃  Successfully finished redacting the repo.                                          ┃"
    echo "┃                                                                                     ┃"
    echo "┃ ${RED}IMPORTANT!!!${NC}                                                                        ┃"
    echo "┃   Before pushing double check that you are NOT pushing to the Private repo          ┃"
    echo "┃   Have someone else check before you push as you will need to force push master     ┃ "
    echo "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"
}

function initialisation() {

    # Replace the repository.yaml with the public key
    echo "${GREEN}Changing Private Repository Yaml to Public Yaml...${NC}"
    rm repository.yaml
    mv public_repo_yaml repository.yaml

    # Make the Public License Active (required to cirumvent auto-build checks)
    mv public_LICENSE LICENSE

    # Remove anon.sh script
    echo "${GREEN}Deleting the anon.sh script...${NC}"
    rm anon.sh

    # Commit the change
    echo "${GREEN}Committing changes...${NC}"
    git add .
    git commit -m "Redacted"
}

function redactCommits() {

    # Rewrite all commits to hide the author's name and email
    echo "${GREEN}Rewriting all commits across all branches to remove Names and Emails of Committer and Author...${NC}"
    git filter-branch --env-filter '
      export GIT_AUTHOR_NAME="REDACTED"
      export GIT_COMMITTER_NAME="REDACTED"
      export GIT_COMMITTER_EMAIL="REDACTED"
      export GIT_AUTHOR_EMAIL="REDACTED"' --tag-name-filter cat -- --branches --tags

    # Delete the old commits
    echo "${GREEN}Deleting references to old commits...${NC}"
    rm -rf .git/refs/original/
}

function updateRemotes() {

    # Delete remotes, which might point to the old commits
    echo "${GREEN}Deleting references to remotes...${NC}"
    for r in `git remote`; do git remote rm $r; done

    #Add Remote to Public repo
    echo "${GREEN}Adding remote to public repo: off-payroll-frontend...${NC}"
    git remote add public git@github.com:hmrc/off-payroll-frontend.git
}

function cleanRepo() {

    # Use BFG Repo Cleaner (https://rtyley.github.io/bfg-repo-cleaner/) to remove references to:
    #  - anon.sh
    #  - repository.yaml
    #  - public_repo_yaml
    # BFG Jar is assumed to be installed at /usr/local/bin
    echo "${GREEN}Cleaning up references to anon.sh, public_repo_yaml and repository.yaml to hide history...${NC}"
    java -jar /usr/local/bin/bfg.jar --delete-files "{anon.sh,public_repo_yaml,repository.yaml}" .
    git reflog expire --expire=now --all && git gc --prune=now --aggressive
}

function abortRedaction() {
    echo "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓"
    echo "┃ ${GREEN}Success:${NC}                            ┃"
    echo "┃  ${GREEN}Redaction Aborted.${NC}                 ┃"
    echo "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"
}

if [[ ${BRANCHES} -gt 1 ]]
then
    echo "Running this will redact: "
    echo "${RED}${BRANCHES} branches${NC} from ${GREEN}'${REPO}'${NC}."
    echo "We recommend only redacting master."
    read -p "$(echo "Do you want to continue anyway? (${GREEN}Y${NC}/${RED}N${NC}): ")" -n 1 -r
    echo    # (optional) move to a new line
    if [[ $REPLY =~ ^[Yy]$ ]]
    then
        redactionOrchestrator
    else
        abortRedaction
    fi
else
    if [[ -f "$BFG" ]]
    then
        read -p "$(echo "Are you sure you want to redact ${GREEN}'${REPO}'${NC}? (${GREEN}Y${NC}/${RED}N${NC}) ")" -n 1 -r
        echo    # (optional) move to a new line
        if [[ $REPLY =~ ^[Yy]$ ]]
        then
            redactionOrchestrator
        else
            abortRedaction
        fi
    else
        echo "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓"
        echo "┃ ${RED}ERROR:                                            ${NC}           ┃"
        echo "┃  ${RED}BFG Repo Cleaner is not installed at /usr/local/bin ${NC}        ┃"
        echo "┃  Install BFG Repo Cleaner from:                              ┃"
        echo "┃     https://rtyley.github.io/bfg-repo-cleaner/               ┃"
        echo "┃  You will need to install it to the directory /usr/local/bin ┃"
        echo "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"
    fi
fi