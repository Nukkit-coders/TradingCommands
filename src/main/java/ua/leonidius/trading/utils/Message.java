package ua.leonidius.trading.utils;

    import cn.nukkit.Player;
    import cn.nukkit.Server;
    import cn.nukkit.command.CommandSender;
    import cn.nukkit.level.Location;
    import cn.nukkit.plugin.PluginBase;
    import cn.nukkit.utils.Config;
    import cn.nukkit.utils.TextFormat;

    import java.io.File;
    import java.io.InputStream;
    import java.text.DecimalFormat;


public enum Message {

    LNG_LOAD_FAIL("Failed to load languages from file. Default message used"),
    LNG_SAVE_FAIL("Failed to save lang file"),
    LNG_PRINT_FAIL("Failed to print message to 'null': \"%1%\""),
    LNG_CONFIG("[MESSAGES] Messages: %1% Language: %2% Save translate file: %1% Debug mode: %3%"),
    CMD_CONSOLE ("You cannot use this command from console."),

    ID_EMPTY ("You hold nothing in your hand."),
    ID_ITEMID ("You are holding an item with ID %1%:%2%."),

    SELL_NOT_SELLING ("You cannot sell this item."),
    SELL_YOU_SOLD ("You have sold %1%x %2% (%3%:%4%) for %5%%6%."),
    SOLD_LOG ("Player %1% has sold %2%x %3% (%4%:%5%) for %6%%7%."),
    SELL_LESS_THAN_ONE ("You cannot sell less than one item."),
    SELL_NO_ITEM ("You have no items to sell."),
    SELL_NO_ITEM_MAX ("Not enough items, max amount will be sold (%1%)."),
    SELL_CREATIVE ("You cannot sell items while you are in creative mode."),

    BUY_NOT_SELLING ("You cannot buy this item."),
    BUY_LESS_THAN_ONE ("You cannot buy less than one item."),
    BUY_NOT_ENOUGH_MONEY ("Not enough money."),
    BUY_YOU_BOUGHT ("You have bought %1%x %2% (%3%:%4%) for %5%$."),
    BOUGHT_LOG ("Player %1% has bought %2%x %2% (%4%:%5%) for %6%$."),
    BUY_NO_SPACE ("Not enough space in your inventory."),
    BUY_NO_SPACE_MAX ("Not enough space in your inventory, max amount will be purchased (%1%)."),
    BUY_NO_MONEY_MAX ("Not enough money, max amount will be purchased (%1%)."),

    LIST_CAN_BUY ("On sale"),
    LIST_CAN_SELL ("You can sell"),
    LIST_EXISTS ("This item is already in the list."),
    LIST_DOESNOT_EXIST ("There is no such an item in the list."),
    LIST_PRICES_IN ("prices in %1%"),
    LIST_NOTHING ("There is nothing in the list."),

    LIST_BUY_ADDED ("Now you can buy %1% (%2%:%3%) for %4%$ per each."),
    LIST_BUY_ADDED_LOG ("Player %1% has added %2% (%3%:%4%) to buying list for %5%$ per each."),
    LIST_SELL_ADDED ("Now you can sell %1% (%2%:%3%) for %4%$ per each."),
    LIST_SELL_ADDED_LOG ("Player %1% has added %2% (%3%:%4%) to selling list for %5%$ per each."),

    LIST_BUY_DELETED ("Now you cannot buy %1% (%2%:%3%)."),
    LIST_BUY_DELETED_LOG ("Player %1% has deleted %2% (%3%:%4%) from list for buying."),
    LIST_SELL_DELETED ("Now you cannot sell %1% (%2%:%3%)."),
    LIST_SELL_DELETED_LOG ("Player %1% has deleted %2% (%3%:%4%) from list for selling."),

    AMOUNT ("amount"),
    PRICE ("price"),
    BID ("bid"),
    START_PRICE ("start price"),

    CMD_ID ("Shows ID of an item in your hand."),
    CMD_BUY ("Buy items."),
    CMD_BUYLIST ("List of items on sale."),
    CMD_SELLLIST ("List of items that you can sell."),
    CMD_SELL ("Sell items."),
    CMD_ADDBUYITEM ("Adding an item to the shop (for buying)."),
    CMD_ADDSELLITEM ("Adding an item to the list for selling."),
    CMD_DELBUYITEM ("Removing an item from the shop."),
    CMD_DELSELLITEM ("Removing an item from the list for selling."),
    CMD_HELP ("Shop help."),
    CMD_BET ("Bid on auction"),
    CMD_AUC ("Start an auction"),

    AUC_START ("Auction has been started! On sale - %1%x %2% (%3%:%4%), start price - %5%, seller - %6%."),

    AUC_TAX_TAKEN ("The starting tax has been taken! (%1%)"),
    AUC_NOTIFICATION ("On auction - %1%x %2% (%3%:%4%), current bid - %5%, time left - %6%"),
    AUC_TIME_LEFT ("Time left - %1%"),
    AUC_SMALLBET ("Your bid must be greater than the current one for one dollar."),
    AUC_FINISHED_WINNER ("Auction has been finished! Winner - %1%."),
    AUC_FINISHED_NOWINNER ("Auction has finished. There were no participants."),
    AUC_FINISHED ("Auction has been finished"),
    AUC_YOU_EARNED ("You have earned %1%$!"),
    AUC_NOBODY ("Nobody have bet."),
    AUC_RUNNING ("Auction is running. Wait for its ending."),
    AUC_NEW_BID ("Player %1% has bet %2%$"),
    AUC_NOT_RUNNING ("There is no auction now."),
    AUC_DURATION ("Auction duration - %1%."),
    AUC_NOT_ENOUGH_MONEY ("Not enough money to pay auction tax (%1%$)."),
    AUC_YOUR ("You cannot bet on your own auction."),
    AUC_CREATIVE ("You cannot start an auction while you are in creative mode."),
    AUC_WINNER_NO_SPACE ("Auction winner has no free space in his inventory. The lot and the tax were returned."),
    AUC_YOU_WON_NO_SPACE ("You won the auction, but there is not enough space in your inventory. The bid were returned."),

    INCORRECT_PARAMS ("Incorrect parameters.");


    private static boolean debugMode = false;
    private static String language = "english";
    private static char c1 = 'a';
    private static char c2 = '2';
    private static boolean saveLanguage = false;

    private static PluginBase plugin = null;




    /**
     * Send current message to log files
     *
     * @param s
     * @return — always returns true.
     * Examples:
     * Message.ERROR_MESSAGE.log(variable1); // just print in log
     * return Message.ERROR_MESSAGE.log(variable1); // print in log and return value true
     */
    public boolean log(Object... s) {
        plugin.getLogger().info(getText(s));
        return true;
    }

    /**
     * Same as log, but will printout nothing if debug mode is disabled
     *
     * @param s
     * @return — always returns true.
     */
    public boolean debug(Object... s) {
        if (debugMode) plugin.getLogger().info(TextFormat.clean(getText(s)));
        return true;
    }



    /**
     * Send message to Player or to ConsoleSender
     *
     * @param sender
     * @param s
     * @return — always returns true.
     */
    public boolean print(CommandSender sender, Object... s) {
        if (sender == null) return Message.LNG_PRINT_FAIL.log(this.name());
        sender.sendMessage(getText(s));
        return true;
    }

    /**
     * Send message to all players or to players with defined permission
     *
     * @param permission
     * @param s
     * @return — always returns true.
     * <p>
     * Examples:
     * Message.MSG_BROADCAST.broadcast ("pluginname.broadcast"); // send message to all players with permission "pluginname.broadcast"
     * Message.MSG_BROADCAST.broadcast (null); // send message to all players
     */
    public boolean broadcast(String permission, Object... s) {
        for (Player player : plugin.getServer().getOnlinePlayers().values()) {
            if (permission == null || player.hasPermission(permission)) print(player, s);
        }
        return true;
    }

    /**
     * Get formated text.
     *
     * @param keys * Keys - are parameters for message and control-codes.
     *             Parameters will be shown in position in original message according for position.
     *             This keys are used in every method that prints or sends message.
     *             <p>
     *             Example:
     *             <p>
     *             EXAMPLE_MESSAGE ("Message with parameters: %1%, %2% and %3%");
     *             Message.EXAMPLE_MESSAGE.getText("one","two","three"); //will return text "Message with parameters: one, two and three"
     *             <p>
     *             * Color codes
     *             You can use two colors to define color of message, just use character symbol related for color.
     *             <p>
     *             Message.EXAMPLE_MESSAGE.getText("one","two","three",'c','4');  // this message will be red, but word one, two, three - dark red
     *             <p>
     *             * Control codes
     *             Control codes are text parameteres, that will be ignored and don't shown as ordinary parameter
     *             - "SKIPCOLOR" - use this to disable colorizing of parameters
     *             - "NOCOLOR" (or "NOCOLORS") - return uncolored text, clear all colors in text
     *             - "FULLFLOAT" - show full float number, by default it limit by two symbols after point (0.15 instead of 0.1483294829)
     * @return
     */
    public String getText(Object... keys) {
        char[] colors = new char[]{color1 == null ? c1 : color1, color2 == null ? c2 : color2};
        if (keys.length == 0) return TextFormat.colorize("&" + colors[0] + this.message);
        String str = this.message;
        boolean noColors = false;
        boolean skipDefaultColors = false;
        boolean fullFloat = false;
        int count = 1;
        int c = 0;
        DecimalFormat fmt = new DecimalFormat("####0.##");
        for (int i = 0; i < keys.length; i++) {
            String s = keys[i].toString();
            if (c < 2 && keys[i] instanceof Character) {
                colors[c] = (Character) keys[i];
                c++;
                continue;
            } else if (s.equals("SKIPCOLOR")) {
                skipDefaultColors = true;
                continue;
            } else if (s.equals("NOCOLORS") || s.equals("NOCOLOR")) {
                noColors = true;
                continue;
            } else if (s.equals("FULLFLOAT")) {
                fullFloat = true;
                continue;
            } else if (keys[i] instanceof Location) {
                Location loc = (Location) keys[i];
                if (fullFloat)
                    s = loc.getLevel().getName() + "[" + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + "]";
                else
                    s = loc.getLevel().getName() + "[" + fmt.format(loc.getX()) + ", " + fmt.format(loc.getY()) + ", " + fmt.format(loc.getZ()) + "]";
            } else if (keys[i] instanceof Double || keys[i] instanceof Float) {
                if (!fullFloat) s = fmt.format(keys[i]);
            }

            String from = (new StringBuilder("%").append(count).append("%")).toString();
            String to = skipDefaultColors ? s : (new StringBuilder("&").append(colors[1]).append(s).append("&").append(colors[0])).toString();
            str = str.replace(from, to);
            count++;
        }
        str = TextFormat.colorize("&" + colors[0] + str);
        if (noColors) str = TextFormat.clean(str);
        return str;
    }

    public String getCleanText (Object... keys) {
        return getText("NOCOLOR", keys);
    }

    private void initMessage(String message) {
        this.message = message;
    }

    private String message;
    private Character color1;
    private Character color2;

    Message(String msg) {
        message = msg;
        this.color1 = null;
        this.color2 = null;
    }

    Message(String msg, char color1, char color2) {
        this.message = msg;
        this.color1 = color1;
        this.color2 = color2;
    }

    Message(String msg, char color) {
        this(msg, color, color);
    }

    @Override
    public String toString() {
        return this.getText("NOCOLOR");
    }

    /**
     * Initialize current class, load messages, etc.
     * Call this file in onEnable method after initializing plugin configuration
     *
     * @param plg
     */
    public static void init(PluginBase plg) {
        plugin = plg;
        language = plg.getConfig().getString("general.language", "default");
        if (language.equalsIgnoreCase("default")) language = Server.getInstance().getLanguage().getLang();
        else if (language.length() > 3) language = language.substring(0, 3);
        //debugMode = plg.getConfig().getBoolean("general.debug-mode",false);
        //saveLanguage = plg.getConfig().getBoolean("general.save-translation",false);
        debugMode = false;
        saveLanguage = true;

        initMessages();
        if (saveLanguage) saveMessages();
        LNG_CONFIG.debug(Message.values().length, language, true, debugMode);
    }

    /**
     * Enable debugMode
     *
    // * @param //debug
     */
    public static void setDebugMode(boolean debug) {
        debugMode = debug;
    }

    public static boolean isDebug() {
        return debugMode;
    }


    private static void initMessages() {
        File f = new File(plugin.getDataFolder() + File.separator + language + ".lng");
        Config lng;
        if (!f.exists()) {
            lng = new Config(f, Config.YAML);
            InputStream is = plugin.getClass().getResourceAsStream("/lang/" + language + ".lng");
            lng.load(is);
            if (!f.delete()) {
                System.gc();
                f.delete();
            }
        } else lng = new Config(f, Config.YAML);
        for (Message key : Message.values())
            key.initMessage(lng.getString(key.name().toLowerCase(), key.message));
    }

    private static void saveMessages() {
        File f = new File(plugin.getDataFolder() + File.separator + language + ".lng");
        Config lng = new Config(f, Config.YAML);
        for (Message key : Message.values())
            lng.set(key.name().toLowerCase(), key.message);
        try {
            lng.save();
        } catch (Exception e) {
            LNG_SAVE_FAIL.log();
            if (debugMode) e.printStackTrace();
        }
    }

    /**
     * Send message (formed using join method) to server log if debug mode is enabled
     *
     * @param s
     */
    public static boolean debugMessage(Object... s) {
        if (debugMode) plugin.getLogger().info(TextFormat.clean(join(s)));
        return true;
    }

    /**
     * Join object array to string (separated by space)
     *
     * @param s
     */
    public static String join(Object... s) {
        StringBuilder sb = new StringBuilder();
        for (Object o : s) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(o.toString());
        }
        return sb.toString();
    }

    /**
     * Print exception message, only if debug mode enabled
     *
     * @param exception
     */
    public static void debugException(Exception exception) {
        if (debugMode) exception.printStackTrace();
    }
}