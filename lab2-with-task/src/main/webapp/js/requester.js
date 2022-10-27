function requestData(params) {
    fetch('controller', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify(params)
    })
        .then(response => {
            if (response.status === 400) {
                throw new Error()
            }
            return response.text()
        })
        .then(text => {
            const json = JSON.parse(text)
            $("#output").html(json['output'])
            current_page = json['page']
        })
        .catch(() => error('Повторите попытку позже'))
}