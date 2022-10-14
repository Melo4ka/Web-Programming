$(document).ready(() => $('svg').on('click', (event) => {
    if (!validateR()) {
        error('Выберите значение R')
        return
    }
    const position = getMousePosition(event)
    const r = $("label[for='" + $('[name="value_R"]:checked').attr('id') + "']").html()
    const x = (position.x - 150) / 100 * r
    const y = (150 - position.y) / 100 * r
    const color = isOnPlot(x, y, r) ? "green" : "red"
    createPointer(position.x, position.y, color)
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

function createPointer(x, y, color) {
    dots.html(`${dots.html()}<circle fill="${color}" 
        cx="${x}" cy="${y}" r="2.25"></circle>`)
}

function isOnPlot(x, y, r) {
    return (x >= 0 && x <= r / 2 && y >= 0 && y <= r && 2 * x + y <= r) || //triangle
        (x >= 0 && x <= r / 2 && y <= 0 && y >= -r) || //rectangle
        (x <= 0 && y <= 0 && x**2 + y**2 <= (r / 2)**2); //circle
}