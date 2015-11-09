package keytyperserver;

public class MacroHandler {

    private String[] commands = null;
    private KeyPresser keyPresser = null;

    public MacroHandler(String macro, KeyPresser keyPresser) {
        this.keyPresser = keyPresser;
        String delims = "[ ]+";
        commands = macro.toUpperCase().split(delims);
    }

    public void process() {
        System.out.println(commands.length);

        for (String s : commands) {
            keyPresser.processCommand(s);
        }
    }

}
