let current_page = 1

$('#submit_button').click(() => {
    if (!validate()) {
        return
    }
    requestData({
        clicked: false,
        x: $('#value_X').val(),
        y: $('#value_Y').val().replace(',', '.'),
        r: $("label[for='" + $('[name="value_R"]:checked').attr('id') + "']").html(),
        page: current_page
    })
})

$('#first_page_button').click(() => {
    if (current_page !== 1) {
        requestData({page: current_page = 1})
    }
})
$('#previous_page_button').click(() => {
    if (current_page !== 1) {
        requestData({page: current_page -= 1})
    }
})
$('#next_page_button').click(() => requestData({page: current_page += 1}))
$('#last_page_button').click(() => requestData({page: current_page = 0}))

function error(message) {
    Swal.fire({
        icon: 'error',
        title: 'Ошибка',
        text: message,
        heightAuto: false
    })
}


