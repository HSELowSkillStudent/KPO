Задание:

Имеется корневая папка. В этой папке могут находиться текстовые файлы, а также другие папки. В других папках также могут находиться текстовые файлы и папки (уровень вложенности может оказаться любым).

В каждом файле может быть ни одной, одна или несколько директив формата:

require ‘<путь к другому файлу от корневого каталога>’

Директива означает, что текущий файл зависит от другого указанного файла.

Необходимо выявить все зависимости между файлами, построить сортированный список, для которого выполняется условие: если файл А, зависит от файла В, то файл А находится ниже файла В в списке. 

Осуществить конкатенацию файлов в соответствии со списком. Если такой список построить невозможно (существует циклическая зависимость), программа должна вывести соответствующее сообщение.

Реализован следующий функционал:
 * Приложение работает на идеальных данных (когда нет циклических зависимостей и все require написаны верно)
 * Задание решено с учетом возможности циклической зависимости (пишется в консоль сообщение о невозможности работы по этойпричине
 * Пишется в консоль сообщение о невозможности работы по причине циклической зависимости и указывается файл(ы) с которыми возникли проблемы
   
   Выбор режима дирректории выполнен в виде ввода абсолютного пути до стартовой дирректории. Возможен ввод слова "this" - в этом случае будет произведена работа в папке, с исполняемым файлом
