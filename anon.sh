#!/bin/sh

# Replace the repository.yaml with the public key
rm repository.yaml
mv public_repo_yaml repository.yaml

# Commit the change
git add .
git commit -m "Private yaml to public"

# Rewrite all commits to hide the author's name and email
for branch in `ls .git/refs/heads`; do
    # We may be doing multiple rewrites, so we must force subsequent ones.
    # We're throwing away the backups anyway.
    git filter-branch --tag-name-filter cat -f --env-filter '
        export GIT_AUTHOR_NAME="REDACTED"
        export GIT_COMMITTER_NAME="REDACTED"
        export GIT_COMMITTER_EMAIL="REDACTED"
        export GIT_AUTHOR_EMAIL="REDACTED"' $branch
done

# Delete the old commits
rm -rf .git/refs/original/

# Delete remotes, which might point to the old commits
for r in `git remote`; do git remote rm $r; done

