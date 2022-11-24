new Vue({
    el: '#auth',
    data: {
        username: '',
        password: '',
        registration: true
    },
    methods: {
        authenticate: function () {
            if (!validateCredentials(this.username, this.password))
                return

            axios.post('api/auth', new URLSearchParams({
                username: this.username,
                password: this.password,
                registration: this.registration
            }))
                .then(() => location.href = 'index.html')
                .catch(error => notifyError(error.response.data))
        }
    }
})

function validateCredentials(username, password) {
    if (username.length < 6) {
        notifyError('Имя пользователя должно быть не менее 6 символов')
        return false
    }
    if (password.length < 6) {
        notifyError('Пароль должен быть не менее 6 символов')
        return false
    }
    if (username.match(/[^a-zA-Z0-9]/)) {
        notifyError('Имя пользователя должно состоять из символов латиницы и цифр')
        return false
    }
    if (password.match(/[^!-~]/)) {
        notifyError('Пароль должен состоять из символов латиницы, цифр и спец. символов')
        return false
    }
    return true
}
