package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.example.FileUtil.createNewFileAtFolder;

public class databaseFiller {

    public static void main(String[] args) {
        final List<String> dataToSave = new ArrayList<>();
        dataToSave.add("В ногах - болото,\n" +
                "А в душе - комары и мухи.\n" +
                "Наталья Коровина, г. Выборг.");
        dataToSave.add("Лучше быть запасливым, чем запасным.");
        dataToSave.add("Мне яйца в плечах не жмут.");
        dataToSave.add("Геродот писал о том, что забыл письменность.");
        dataToSave.add("Спешат только Чип и Дейл, а пацаны тянут лямку.");
        dataToSave.add("Антон: Скажите, вы не знаете, где мы можем познакомиться?");
        dataToSave.add("Антон: Стойте, стоять будем.");
        dataToSave.add("Вы на меня не пяльтесь, у меня денег нет. Могу только себя показать. Оптом.");
        dataToSave.add("Надоело быть кинутой, хочу стать брошенной.");
        dataToSave.add("Если ты слишком худая, то мужчины вокруг тебя начинают толстеть.");
        dataToSave.add("Жизнь такое же говно, как и айфон.");
        dataToSave.add("Ложь – это форма вежливости.");
        dataToSave.add("Зона есть зона, даже если это зона комфорта.");
        dataToSave.add("Не грусти из-за разбитой тачки, разобьёшь другую.");
        dataToSave.add("Лучше иметь длинные руки, чем длинные погоны.");
        dataToSave.add("Если у тебя завёлся сосед – бей его.");
        dataToSave.add("Идите на хуй. Если кому-то хуй не нравится, то идите в жопу.");
        dataToSave.add("Вчера в говно, сегодня в гроб.");
        dataToSave.add("Родня любит бесплатно.");
        dataToSave.add("Быть мудаком – слишком большая цена за то, чтобы тебя любили женщины.");
        dataToSave.add("Есть те, кто понимает, и есть те, кому сказали как понимать.");
        dataToSave.add("Привет! Я не умею соблазнять, давай лучше ты.");
        dataToSave.add("У вас есть кошка? Она такая же красивая?");
        dataToSave.add("Антон: Мои планы на выходные подозрительно хорошо сочетаются с формой твоих бёдер.");
        dataToSave.add("Готовься к серьёзному испытанию, я собираюсь сделать тебе комплимент.");
        dataToSave.add("Когда ты был маленьким, то думал, что умрёшь в один день.");
        dataToSave.add("На мытой машине быстрее не поедешь.");
        dataToSave.add("Умение пиздеть грамотно и без мата – большая редкость.");
        dataToSave.add("Трусы у пацана могут быть мокрыми только при выходе из бани.");
        dataToSave.add("Для мужика главное – пожрать, для бабы – посрать.");
        dataToSave.add("Бойся не того, что будет, а того, чего не будет.");
        dataToSave.add("Выпивши, все мужики немного геи.");
        dataToSave.add("Любовь превращает пизду в золото.");
        dataToSave.add("Разум человека на 80% состоит из воды.");
        dataToSave.add("Философия – это бессмысленная попытка объяснить, что всё бессмысленно.");
        dataToSave.add("Чтобы дети в детских садах ели какашки, их надо обильно обсыпать сахарной пудрой. На вопрос «зачем?», детский сад отвечает: «так вкуснее».");
        dataToSave.add("3% мирового запаса золота находится в твоём сердце.");
        dataToSave.add("Антон: Девушка, ваши родители были перфекционистами.");
        dataToSave.add("Антон: Я человек-праздник, хочу тебя и подарочки.");
        dataToSave.add("У меня на балконе растёт такая же хрень, как и у тебя на аватарке.");
        dataToSave.add("Размышлять на тему «откуда я» весело, но вредно. Лучше вспомните, «куда я».");
        dataToSave.add("Бэтмен использует своё финансовое преимущество против преступников, снимая фильмы про себя.");
        dataToSave.add("Чарльз Дарвин утверждал, что все животные произошли от него.");
        dataToSave.add("Если во время любовной прелюдии не зажигать свет, то партнёр с меньшей вероятностью откажется от сладкого.");
        dataToSave.add("Татаро-монголы славились своей жестокостью, а монголо-татары были добрыми.");
        dataToSave.add("В игре Paranoia не хватает игроков, что заставляет задуматься.");
        dataToSave.add("Ты как хлеб с картошкой – очень хороший и простой человек.");
        dataToSave.add("Антон: Что это с вами? Вы девушка, что ли?");
        dataToSave.add("Алиса: У меня бывают запои и месячные, но я девушка порядочная.");
        dataToSave.add("Каждый год в Северной Ирландии люди вырезают на деревьях надписи, чтобы сообщить миру, что они всё ещё живы.");
        dataToSave.add("Зимой в Финляндии можно покататься в гигантском сапоге.");
        dataToSave.add("Во всех зоопарках мира сидит один и тот же лев.");
        dataToSave.add("Советским солдатам приходилось есть мыло из алюминиевых тарелок, потому что других не было.");
        dataToSave.add("Африканцы верят в магию цифр: если сложить вместе три цифры – получится одно число.");
        dataToSave.add("Алиса: Ты такой неотразимо недоразвитый.");
        dataToSave.add("Алиса: Вы не откроете мне эту дверь? Я опаздываю на свидание с вами.");
        dataToSave.add("Смотри мне, не вздумай влюбиться. А то двое влюблённых – это уже перебор.");
        dataToSave.add("Музей геноцида получил 5000 долларов на развитие.");
        dataToSave.add("6% жителей России не знают о том, что все остальные люди не спят на лужайке.");
        dataToSave.add("В Steam появилась игра.");
        dataToSave.add("Nokia показала смартфон-невидимку.");
        dataToSave.add("На территории России прошли открытые соревнования по нарушению авторских прав.");
        dataToSave.add("Во время Второй мировой войны моряки не падали в обморок, если они ели.");
        dataToSave.add("Первый человек, который выиграл в лотерею, выиграл вместе с выигрышем и равнозначный налог.");
        dataToSave.add("В огород явно лживого противоречия бросили первый камень. Камень лжи вернул в огород правду. Почему?");
        dataToSave.add("Игрокам для поиска предметов в Fable 4 дадут мозг.");
        dataToSave.add("Хитрые художники умеют рисовать пиво на пустых банках из-под пива.");
        dataToSave.add("В споре между оптимистом и пессимистом побеждает Вселенная.");
        dataToSave.add("Антон: Между нами есть сцепление, его надо выжать.");
        dataToSave.add("Пароль «я тебя люблю», угадал?");
        dataToSave.add("Новости: Параллельный мир станет более многолюдным.");
        dataToSave.add("Самых опасных преступников держат в аквариумах.");
        dataToSave.add("Во время родов младенцы часто проходят через 3D туннель.");
        dataToSave.add("В 1792 году во время битвы при Ватерлоо с аэродрома взлетела сложенная в несколько раз конституция Франции, которая зацепилась за лошадь пруссака и продержалась до конца битвы.");
        dataToSave.add("Если посмотреть на людей с известной долей сарказма, то самые ушлые из них владеют бизнесом, в котором их собственное мнение никого не интересует.");
        dataToSave.add("Наука, которая сможет обеспечить человеческое бессмертие, будет называться информатикой.");
        dataToSave.add("Антон: Я так понял, вы одна из этих? Из красивых?");
        dataToSave.add("Мне нравится, как ты дышишь.");
        dataToSave.add("Я Дима из Москвы. Почему вы ещё не разделись?");
        dataToSave.add("В Кении нельзя пить воду из-под крана, потому что туда могли попасть кенгуру.");
        dataToSave.add("У Халка в диалогах появилась возможность нагибать персонажей.");
        dataToSave.add("Антон: Мы пока не знакомы, но я по тебе скучал.");
        dataToSave.add("Антон: И не стыдно тебе быть такой красивой и одинокой?");
        dataToSave.add("Антон: Я буду держать тебя за руку, пока ты сидишь в туалете.");
        dataToSave.add("В Голландии существует сверхсекретный закон, который практически не работает, потому что из-за секретности его сторонники не могут собрать достаточное количество подписей.");
        dataToSave.add("Антон: Ты мне ничего плохого не сделала, но я всё равно хочу с тобой познакомиться.");
        dataToSave.add("Разносчики пиццы в Испании носят маску наизнанку, чтобы показать, что они не люди и не съедят вашу пиццу.");
        dataToSave.add("Когда вам грустно – улыбнитесь, вы никому не помешаете.");
        dataToSave.add("Когда детям в цирке показывают фокус, они спрашивают: «А когда эти тётки начнут танцевать?».\n");
        dataToSave.add("Астраханский губернатор ездит в детской коляске с охраной.");
        dataToSave.add("Меня зовут Катя, я очень приятно познакомилась");
        dataToSave.add("Антон: Предлагаю социализацию на локальном уровне.");
        dataToSave.add("Перед смертью Цицерон сказал: «Кто не хочет умереть завтра, может умереть сегодня».");
        dataToSave.add("Если пройтись босиком по стеклу, то можно увидеть, как осыпается и становится мутным воздух вокруг вас.");
        dataToSave.add("Здравствуйте, не могли бы вы пригласить меня к себе? Я не знакомлюсь на улице.");
        dataToSave.add("У вас не найдётся полжизни для меня?");
        dataToSave.add("В дореволюционной России чай называли «Ах ты, дрянь такая!»");
        dataToSave.add("Согласно первому закону короля Артура, единственный закон, принятый Артуром, был запрещён.");
        dataToSave.add("Жопа – это всего лишь удобный случай для обдумывания своей жизни.");
        dataToSave.add("Закат цивилизации – это когда гомосексуалисты, снимая видео для взрослых в общественном месте, пользуются презервативами.");
        dataToSave.add("Ветер дует потому, что планета крутится.");
        dataToSave.add("Человечество делится на тех, кто носит джинсы, и на тех, у кого денег больше.");
        dataToSave.add("Ополаскиватель рта на 98% состоит из воды, но лучше об этом не думать.");
        dataToSave.add("Студия Unreal попросила геймеров стать невидимыми.");
        dataToSave.add("Человек был одомашнен на территории Казахстана около 12 тысяч лет назад.");
        dataToSave.add("Китайцы едят руками, чтобы японцы не украли их еду.");
        dataToSave.add("Среднестатистический болельщик футбольного клуба Бавария пропускает около 3500 голов за свою карьеру.");
        dataToSave.add("Людям с синдромом рассеянного внимания можно достаточно долго читать лекции, если, конечно, они слушают.");
        dataToSave.add("Антон: Привет, ты симпатичная, пойду познакомлюсь с тобой.");
        dataToSave.add("Гены человека, отвечающие за интерес к жизни, были отвязаны от других генов ещё в утробе матери.");
        dataToSave.add("Исследователям из Гарвардского университета удалось научить беспризорников курить в четыре раза быстрее.");
        dataToSave.add("Динозавры не планировали вымирать.");
        dataToSave.add("Во время расследования убийства на Соломоновых островах, детектива больше всего удивило несоответствие между тем, как выглядит и о чём говорит убитый.");



        String filename = "quotes.csv";
        createNewFileAtFolder("database", filename);
        String path = "database" + File.separator + filename;
        FileUtil.writeLines(path, dataToSave);

        List<String> strings = FileUtil.readLines(path);
    }
}
