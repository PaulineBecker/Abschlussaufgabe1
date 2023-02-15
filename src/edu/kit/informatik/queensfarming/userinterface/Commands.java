package edu.kit.informatik.queensfarming.userinterface;

/**
 * List of available commands with their command line interaction expressions.
 *
 * @author uyxib
 * @verion 1.0
 */
public enum Commands {
    BUY_VEGETABLE("buy") {
        @Override public String execute(){
            return null;
        }
    },
    BUY_LAND("buy") {
        @Override public String execute(){
            return null;
        }
    },
    PLANT("plant") {
        @Override public String execute(){
            return null;
        }
    },
    HARVEST("harvest") {
        @Override public String execute(){
            return null;
        }
    },
    SELL("sell") {
        @Override public String execute(){
            return null;
        }
    },
    SHOW_BARN("show barn") {
        @Override public String execute(){
            return null;
        }
    },
    SHOW_BOARD("show board") {
        @Override public String execute(){
        return null;
        }
    },
    SHOW_MARKET("show market") {
        @Override public String execute(){
            return null;
        }
    },
    END_TURN("end turn") {
        @Override public String execute(){
            return null;
        }
    },
    QUIT("quit") {
        @Override public String execute(){
            return null;
        }
    };


    Commands(String representation) {
    }

    abstract String execute();
}
