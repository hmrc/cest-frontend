
        const showAllSpan = document.getElementsByClassName('govuk-accordion__show-all-text')[0]
        const showAllButton = document.querySelector('.govuk-accordion__controls button')
const accordionSections = document.getElementsByClassName('govuk-accordion__section')
const setAccordionMessages = function() {

// set main openall/showall message
if (showAllButton.getAttribute('aria-expanded') == 'false') {
showAllSpan.textContent = "@messages("site.accordion.openAll")"
} else {
showAllSpan.textContent = "@messages("site.accordion.closeAll")"
}

// set section button messages
for (let i = 0; i <= accordionSections.length - 1; i++) {
const sectionButton = document.getElementsByClassName('govuk-accordion__section-button')[i]
const message = sectionButton.getAttribute('aria-expanded') == 'false' ? '@messages("site.accordion.openSection")' : '@messages("site.accordion.closeSection")'
sectionButton.querySelector('span.govuk-accordion__section-toggle-text').textContent = message;
}

}
if(showAllButton) {
setAccordionMessages()
showAllButton.addEventListener("click", setAccordionMessages);
for (let i = 0; i <= accordionSections.length - 1; i++) {
document.getElementsByClassName('govuk-accordion__section-button')[i].addEventListener("click", function() {
setTimeout(setAccordionMessages, 0);
});
}
}
