function requestData(params) {
    fetch('controller', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify(params)
    })
        .then(response => response.text())
        .then(text => {
            if (text.includes('<td>')) {
                $("#output").html(text)
            }
        })
        .catch(() => error('Повторите попытку позже'))
}