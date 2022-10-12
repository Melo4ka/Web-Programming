$(document).ready(() => $('svg').on('click', (event) => {
    if (!validateR()) {
        error('Выберите значение R')
        return
    }
    const position = getMousePosition(event)
    createPointer(position.x, position.y)
    const r = $("label[for='" + $('[name="value_R"]:checked').attr('id') + "']").html()
    const x = (position.x - 150) / 100 * r
    const y = (150 - position.y) / 100 * r
    requestData({
        clicked: true,
        x: x,
        y: y,
        r: r
    })
}))

const dots = $('#dots')

$('input[type=checkbox]').on('click', () => dots.empty())

function getMousePosition(event) {
    const rect = document.querySelector("svg").getBoundingClientRect()
    return {
        x: event.clientX - rect.left,
        y: event.clientY - rect.top
    };
}

function createPointer(x, y, color="black") {
    dots.html(`${dots.html()}<circle fill="${color}" 
        cx="${x}" cy="${y}" r="2.25"></circle>`)
}