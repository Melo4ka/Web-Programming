setInterval(() => {
    $('.clock_bar').html(moment().format('D.MM.YYYY HH:mm:ss'))
}, 1000);