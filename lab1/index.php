<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Web | Lab #1</title>
      <link href="assets/images/favicon.ico" rel="icon">
      <script src="chibi-min.js"></script>
      <style>
         td.header {
           background-color: #3399ff;
           border: none;
           border-radius: 0px 0px 100px 100px;
           color: #fff;
           font-size: 22px;
         }
         td.footer {
           background-color: #3399ff;
           border: none;
           border-radius: 100px 100px 0px 0px;
           color: #fff;
           width: 100%;
         }
         .social {
           border-radius: 50%;
           height: 50px;
           margin: 7px;
           box-shadow: 0px 0px 5px 3px rgba(0, 0, 0, 0.2);
         }
         .social:hover {
           box-shadow: 0px 0px 5px 2px rgba(255, 255, 255, 1);
         }
         .result_table {
           padding: 5px;
           margin-bottom: 15px;
           width: 70%;
           border: none;
           border-radius: 35px;
           background-color: #3399ff;
           color: #fff;
           margin-left: auto;
           margin-right: auto;
         }
         body {
           margin: 0px;
           padding: 0px;
           text-align: center;
           font-family: sans-serif;
         }
         td, th {
           text-align: center;
           border-spacing: 0px 20px;
         }
         thead th {
           text-decoration: underline;
         }
         input {
           margin-top: 10px;
           margin-bottom: 25px;
         }
         label {
           font-size: 20px;
           position: relative;
           top: -16px;
           cursor: pointer;
         }
         input[type=radio] {
           width: 20px;
           height: 50px;
           cursor: pointer;
         }
         input[type=text] {
           font-size: 15px;
           outline: 3px solid #3399ff;
         }
         input[type=button], input[type=reset] {
           font-size: 15px;
           color: #fff;
           background-color: #3399ff;
           border-radius: 7px;
           padding: 8px;
           margin: 5px;
           margin-bottom: 20px;
           border: none;
           cursor: pointer;
         }
      </style>
   </head>
   <body>
      <table id="main_table">
         <tr>
            <td class="header" colspan="2">
               Андриенко Сергей<br>
               Группа: P32092<br>
               Вариант: 901
            </td>
         </tr>
         <tr height="75%">
            <td>
              <svg height="300" width="300">
                <polygon fill="#3399ff" fill-opacity="0.35" stroke="blue" points="100,150 150,150 150,100"></polygon>
                <rect fill="#3399ff" fill-opacity="0.35" stroke="blue" x="100" y="150" height="100" width="50"></rect>
                <path fill="#3399ff" fill-opacity="0.35" stroke="blue" d="M 150 250 A 100 100, 90, 0, 0, 250 150 L 150 150 Z"></path>

                <line stroke="black" x1="0" x2="300" y1="150" y2="150"></line>
                <line stroke="black" x1="150" x2="150" y1="0" y2="300"></line>
                <polygon fill="black" points="150,0 144,15 155,15"></polygon>
                <polygon fill="black" points="300,150 285,156 285,144"></polygon>

                <line stroke="black" x1="200" x2="200" y1="155" y2="145"></line>
                <line stroke="black" x1="250" x2="250" y1="155" y2="145"/>

                <line stroke="black" x1="50" x2="50" y1="155" y2="145"/>
                <line stroke="black" x1="100" x2="100" y1="155" y2="145"/>

                <line stroke="black" x1="145" x2="155" y1="100" y2="100"/>
                <line stroke="black" x1="145" x2="155" y1="50" y2="50"/>

                <line stroke="black" x1="145" x2="155" y1="200" y2="200"/>
                <line stroke="black" x1="145" x2="155" y1="250" y2="250"/>

                <text fill="black" x="190" y="140">½R</text>
                <text fill="black" x="245" y="140">R</text>

                <text fill="black" x="40" y="140">-R</text>
                <text fill="black" x="85" y="140">-½R</text>

                <text fill="black" x="160" y="105">½R</text>
                <text fill="black" x="160" y="55">R</text>

                <text fill="black" x="160" y="205">-½R</text>
                <text fill="black" x="160" y="255">-R</text>

                <text fill="black" x="160" y="10">Y</text>
                <text fill="black" x="290" y="140">X</text>

                <circle fill="black" cx="150" cy="150" r="2"></circle>
              </svg>
            <td>
               <form>
                  Выберите значение X<br>
                  <?php
                     $number = 1;
                     for ($i = -4; $i <= 4; ++$i) {
                       echo "
                         <input id=\"value_X$number\" name=\"value_X\"
                          type=\"radio\" value=\"$i\">
                         <label for=\"value_X$number\">$i</label>
                         ";
                       ++$number;
                     }
                     ?>
                  <br>Введите значение Y<br>
                  <input id="value_Y" type="text" name="value_Y" maxlength="6"
                     placeholder="Введите число в диапазоне [-5;5]" size="30">
                  <br>Выберите значение R<br>
                  <?php
                     for ($i = 1; $i <= 5; ++$i) {
                       echo "<input type=\"button\" name=\"value_R\" value=\"$i\">";
                     }
                     ?>
                  <br><input type="reset" value="Очистить">
               </form>
            <td>
         </tr>
         <tr>
            <td colspan="2">
               <table class="result_table">
                  <thead>
                     <tr>
                        <th>Номер</th>
                        <th>Значение X</th>
                        <th>Значение Y</th>
                        <th>Значение R</th>
                        <th>Попадание</th>
                        <th>Время нажатия</th>
                        <th>Время выполнения, нс</th>
                     </tr>
                  </thead>
                  <tbody id="result_table"></tbody>
               </table>
            </td>
         </tr>
         <tr>
            <td class="footer" colspan="2">
               <a href="https://github.com/Melo4ka"><img class="social" src="assets/images/github.jpg"></a>
               <a href="https://vk.com/meldren"><img class="social" src="assets/images/vk.png"></a>
               <a href="https://telegram.me/meldren"><img class="social" src="assets/images/telegram.png"></a>
               <br>© 2022 Andrienko Sergei. All rights reserved.
            </td>
         </tr>
      </table>
      <script src="validator.js"></script>
      <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
   </body>
</html>
