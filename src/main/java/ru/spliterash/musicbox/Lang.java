package ru.spliterash.musicbox;


import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.spliterash.musicbox.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@SuppressWarnings({"ArraysAsListWithZeroOrOneArgument", "SpellCheckingInspection", "unused", "RedundantSuppression"})
public enum Lang {
    NO_PEX(
            "&6Sry no perms",
            "&6Похоже у тебя нет разрешения на это действие"),
    // В консоли русский вариант будет смотреться... да никак не будет, UTF-8 👍👍👍
    ONLY_PLAYERS("Sry, but only players can execute this command"),
    SONG_NAME("&6{song}"),
    SONG_LORE(
            Arrays.asList(
                    "&7Length: {lenght}",
                    "&7Author: &6{author}",
                    "&7Original author: &6{original_author}"
            ),
            Arrays.asList(
                    "&7Длина: {lenght}",
                    "&7Автор: &6{author}",
                    "&7Оригинальный автор: &6{original_author}"
            )
    ),
    GUI_TITLE("&6MusicBox {container} &b{page}&6/&b{last_page}"),
    FOLDER_FORMAT("&e{folder}"),
    CURRENT_PLAYNING(
            "&eСейчас играет &b{song}",
            "&eNow playing &b{song}"),
    ADD_CONTAINER_TO_PLAYLIST(
            Arrays.asList("&bRight click&7 to add your playlist"),
            Arrays.asList("&bПравый клик&7 чтобы добавить в своей плейлист музыку отсюда")
    ),
    ADD_MUSIC_TO_PLAYLIST(
            Arrays.asList("&bRightClick&7 to add this song to playlist"),
            Arrays.asList("&bПравый клик&7 чтобы добавить эту мелодию в плейлист")
    ),
    COMMAND_HELP_SHOP(
            "&b/musicbox shop (name)&6- Open disc shop or buy disc with name",
            "&b/musicbox shop (name)&6- Открыть магазин дисков или купить пластинку с именем"),
    COMMAND_HELP_GET(
            "&b/musicbox get (name)&6- Open get disc GUI or get disc with name",
            "&b/musicbox get (name)&6- Открыть инвентарь получения дисков или получить диск с именем"
    ),
    COMMAND_HELP(
            Arrays.asList(
                    "&b/musicbox &6- Open music gui",
                    "&b/musicbox play [song_name] &6- Play song with name"
            ),
            Arrays.asList(
                    "&b/musicbox &6- Открыть инвентарь с музыкой",
                    "&b/musicbox play [имя] &6- Воспроизвести трек"
            )
    ),
    BUY_MUSIC_LORE(
            Arrays.asList(
                    "&7==========================",
                    "&eClick to buy this disc",
                    "&7Price: &6{price} $"
            ),
            Arrays.asList(
                    "&7==========================",
                    "&eНажми чтобы купить эту пластинку",
                    "&7Цена: &6{price} $"
            )
    ),
    CANT_SWITCH(
            "You cant switch play mode",
            "&6Вы не можете поменять режим проигрывания"),
    NEXT(
            "&6Next",
            "&6Вперёд"),
    BUY_CONTAINER_LORE(
            Arrays.asList(
                    "&7==========================",
                    "&ePress &cright&e to buy this box",
                    "&7Price: &6{price} $"
            ),
            Arrays.asList(
                    "&7==========================",
                    "&eНажми &cправой кнопкой&e чтобы купить весь этот сундук",
                    "&7Цена: &6{price} $"
            )),
    NO_HAS_MONEY(
            "&6You don't have enough &b{amount}&6 to buy this",
            "&6Вам не хватает &b{amount}&6 чтобы купить это"),
    NO_INVENTORY_SPACE(
            "&6You dont have space in your inventory",
            "&6У вас нет места в инвентаре"),
    DISC_BUYED(
            "&6You have successfully purchased a disc &b{disc}",
            "&6Вы успешно купили диск &b{disc}");
    /**
     * Оригинальные переводы
     * 0 индекс - англиский
     * 1 индекс - русский
     */
    private final Object[] original = new Object[2];
    private Object selected;

    /**
     * Конструктор для простых строк
     *
     * @param en На англиском
     * @param ru На русском
     */
    Lang(String en, String ru) {
        original[0] = en;
        original[1] = ru;
    }

    /**
     * Конструктор для многострочных переводов
     *
     * @param en На англиском
     * @param ru На русском
     */
    Lang(List<String> en, List<String> ru) {
        original[0] = en;
        original[1] = ru;
    }

    Lang(List<String> en) {
        this(en, en);
    }

    Lang(String en) {
        this(en, en);
    }

    public static void reload(File folder, String lang) {
        File langFile = new File(folder, lang + ".yml");
        int index;
        if (lang.equals("ru"))
            index = 1;
        else
            index = 0;
        fill(langFile, index);
    }

    private static void fill(File langFile, int index) {
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(langFile);
        boolean saveNeed = false;
        for (Lang value : values()) {
            Object obj = conf.get(value.name());
            if (obj == null) {
                obj = value.original[index];
                conf.set(value.name(), obj);
                saveNeed = true;
            }
            if (obj instanceof String) {
                value.selected = StringUtils.t(obj.toString());
            } else {
                //noinspection unchecked
                List<String> list = (List<String>) obj;
                value.selected = StringUtils.t(list);
            }
        }
        if (saveNeed) {
            try {
                conf.save(langFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        if (isString()) {
            return selected.toString();
        } else {
            //noinspection unchecked
            List<String> list = (List<String>) selected;
            return String.join("\n", list);
        }
    }

    private boolean isString() {
        return selected instanceof String;
    }

    public List<String> toList(String... replace) {
        if (isString()) {
            ArrayList<String> list = new ArrayList<>();
            String text = StringUtils.replace(selected.toString(), replace);
            list.add(text);
            return list;
        } else if (replace.length > 0) {
            //noinspection unchecked
            return ((List<String>) selected)
                    .stream()
                    .map(s -> StringUtils.replace(s, replace))
                    .collect(Collectors.toList());
        } else
            //noinspection unchecked
            return new ArrayList<>(((List<String>) selected));
    }

    public BaseComponent[] toComponent(String... replace) {
        if (isString())
            return TextComponent.fromLegacyText(toString(replace));
        else {
            //noinspection unchecked
            return ComponentUtils.join(((List<String>) selected)
                    .stream()
                    .map(s -> StringUtils.replace(s, replace))
                    .collect(Collectors.toList()), "\n");
        }
    }

    public String toString(String... replace) {
        return StringUtils.replace(toString(), replace);
    }

    public String[] toArray() {
        return toList().toArray(new String[0]);
    }

    public String toPlainText(String... replace) {
        return ChatColor.stripColor(toString(replace));
    }
}
