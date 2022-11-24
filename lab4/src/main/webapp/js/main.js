new Vue({
    el: '#main',
    data: {
        x: undefined,
        y: undefined,
        r: undefined,
        results: undefined
    },
    created: function () {
        this.clearFields()
        axios.get('api/main')
            .then(response => this.results = response.data)
            .catch(() => location.href = 'auth.html')
    },
    methods: {
        addResult: function (x, y, r, clicked) {
            axios.post('api/main', new URLSearchParams({
                x: x,
                y: y,
                r: r,
                clicked: clicked
            }))
                .then(response => this.results.push(response.data))
                .catch(error => notifyError(error.response.data.message))
        },
        addResultFromForm: function () {
            const yString = parseFloat(this.y.toString().replace(',', '.'))
            if (isNaN(yString) || yString < -5 || yString > 3) {
                notifyError('Y должен находиться в диапазоне [-5;3]')
                return
            }
            this.addResult(this.x, yString, this.r, false)
        },
        addResultFromPlot: function (event) {
            const rect = document.querySelector('svg').getBoundingClientRect()
            const x = ((event.clientX - rect.left) - 150) / 100 * this.r
            const y = (150 - (event.clientY - rect.top)) / 100 * this.r
            this.addResult(x, y, this.r, true)
        },
        clearResults: function () {
            axios.delete('api/main')
                .then(() => this.results = [])
                .catch(() => notifyError('Не удалось очистить таблицу'))
        },
        logout: function () {
            axios.delete('api/auth')
            location.href = 'auth.html'
        },
        clearFields: function () {
            this.x = '0'
            this.y = ''
            this.r = '1'
        }
    },
    filters: {
        moment: function (date) {
            return moment(date).format('DD.MM.yy HH:mm:ss');
        },
        success: function (success) {
            return success ? 'Да' : 'Нет'
        }
    }
})

function isOnPlot(x, y, r) {
    console.log(this.r)
    return (x >= -r && x <= 0 && y >= 0 && y <= r / 2) || //rectangle
        (x >= 0 && x <= r / 2 && y >= 0 && y <= r && 2 * x + y <= r) || //triangle
        (x >= 0 && y <= 0 && x**2 + y**2 <= r**2) //circle
}