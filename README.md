Требуется написать программу, которая выводит для выбранного пользователя
список его задач, а также план выполнения задач на выбранный месяц. 

Данные должны браться из базы данных, в базе должно быть как минимум две
таблицы: Сотрудник и Задача. У каждого сотрудника есть список его задач. Для
определения списка полей в таблице «Задача» смотри первый рисунок. Для тестового
задания достаточно иметь как минимум 2-х сотрудников, по 2 задачи на каждого а также
показать 2 месяца. Данные должны быть статически записаны в базу данных, приложение
должно только отображать данные в базе данных.

Требования к реализации
1. Пользовательский интерфейс должен быть реализован на Swing.
2. Hibernate для работы с базой.

Приветствуется
1. Spring
2. Log4j
3. I18n
4. Maven
5. Hibernate annatation

Результат работы
1. Исполняемый JAR файл (чтоб можно было запустить строкой java -jar test.jar)
2. SQL скрипт для создания базы данных и скрипт для вставки тестовых данных (для
MySQL) или использование другого хранилища данных доступных в Hibernate
(embeded database, XML file).
3. Конфигурационный файл для настройки связи с базой данных.
4. ZIP архив с исходным кодом и описанием особенностей настройки проекта/запуска
программы если необходимо.
