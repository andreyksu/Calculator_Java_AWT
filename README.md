### The project is an calculator with analog clock. GUI is based on AWT and calculation based on "Reverse Polish Notation" algorithm or pass calculation to JS.

**Описание:**
Проект представляет собой калькулятор, где GUI основан на AWT. Вычисления производятся с использованием польской обратной записи. Результаты расчётов можно сохранять в файл в формате: _<исходная строка для расчёта><результат>_
Также производится логирование через log4j.

**Общее:**
1. Точкой входа является класс MainClass пакета *ru.andreyksu.annikonenkov.entrance*.<br>
 > В данном классе создается модель и контроллер, которому передается ссылка на модель.
2. Далее в контроллере конструируется представление/въюха.
3. Все действия выполняемые с представлением обрабатываются контроллером и делегируются модели.
4. Модель состоит из:
 > Исполнителей расчёта (JS или польская обратная запись)
 > Сохранения результатов расчётов в файл.

**Для сборки продукта нужно:**
1. Установить *Maven*
2. Перейти в "корень" проекта
3. Выполнить команду<br>
```bash
mvn clean package
```
4. В результате, в каталоге target получим исполняемый *.zip* архив. Внутри которого содержится исполняемый *.jar*, каталог *./lib* с необходимыми зависимостями, файл для запуска приложения *.sh*.  

Проект является первым/пробным и соответственно содержит по коду недочеты разного рода.
Цель проекта состоит в изучении maven, log4j, алгоритма *польской обратной записи*, *GUI* на JAVA - по хорошему нужно перевести на Swing или JavaFX.
