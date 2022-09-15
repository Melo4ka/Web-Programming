$().loaded(() => {
  $("#main_table").attr("height", window.screen.height * 1.1)
  requestData({
    timezone: Intl.DateTimeFormat().resolvedOptions().timeZone
  }, false)
})

$("[name='value_R']").each(element => $(element).on("click", () => {
  if (!isValid()) {
    return
  }
  requestData({
    value_X: $("input[type=radio]:checked").val(),
    value_Y: $("#value_Y").val().replace(",", "."),
    value_R: element.value
  }, true)
}))

function requestData(params, broadcast) {
  fetch("data.php?" + new URLSearchParams(params))
  .then(response => response.text())
  .then(text => {
    const table = $("#result_table")
    if (broadcast) {
      Swal.fire('Успех', 'В таблицу добавлены новые данные', 'success')
    }
    table.html(text)
  })
  .catch(() => Swal.fire('Ошибка', 'Повторите попытку позже', 'error'))
}

function isValid() {
  let x = $("input[type=radio]:checked")
  if (!x.length) {
    Swal.fire('Ошибка', 'Не указано значение X', 'error')
    return false
  }
  let value_Y = parseFloat($("#value_Y").val().replace(",", "."))
  if (isNaN(value_Y) || value_Y < -5.0 || value_Y > 5.0) {
    Swal.fire('Ошибка', 'Некорректное значение Y', 'error')
    return false
  }
  return true
}
