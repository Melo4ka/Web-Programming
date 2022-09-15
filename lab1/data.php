<?php
session_start();
if (!isset($_SESSION["rows"])) {
  $_SESSION["rows"] = array();
  $_SESSION["timezone"] = $_GET["timezone"] ?? "Europe/Moscow";
  return;
}
foreach ($_SESSION["rows"] as $row) echo $row;
if (count($_GET) > 1) {
  $x = (int) $_GET["value_X"];
  $y = round($_GET["value_Y"], 3);
  $r = (int) $_GET["value_R"];
  if (is_valid($x, $y, $r)) {
    add_row($x, $y, $r);
  }
}

function is_valid($x, $y, $r) {
  return $x >= -4 && $x <= 4 && $y >= -5 && $y <= 5 && $r >= 1 && $r <= 5;
}

function is_dot_on_plot($x, $y, $r) {
  return ($x >= -$r / 2 && $x <= 0 && $y >= 0 && $y <= $r / 2) ||
        ($x >= -$r / 2 && $x <= 0 && $y <= 0 && $y >= -$r) ||
        ($x >= 0 && $y <= 0 && pow($x, 2) + pow($y, 2) <= pow($r, 2));
}

function add_row($x, $y, $r) {
  $number = count($_SESSION["rows"]) + 1;
  $onPlot = is_dot_on_plot($x, $y, $r) ? "Да" : "Нет";
  $dateTime = new DateTime("now", new DateTimeZone($_SESSION["timezone"]));
  $formattedTime = $dateTime->format("d.m.y H:i:s");
  $executionTime = round((microtime(true) -
    $_SERVER["REQUEST_TIME_FLOAT"]) * 1000, 3);
  $row = "<tr>
    <td>$number</td>
    <td>$x</td>
    <td>$y</td>
    <td>$r</td>
    <td>$onPlot</td>
    <td>$formattedTime</td>
    <td>$executionTime</td>
    </tr>";
  array_push($_SESSION["rows"], $row);
  echo $row;
}
