package formatting;

import utilities.UCharacters;

import java.util.Collections;

/**
 * Created by gvoiron on 06/07/16.
 * Time : 22:23
 */
public abstract class AFormatter {

    private int indentationLevel;

    public AFormatter() {
        this.indentationLevel = 0;
    }

    public String indent(String toIndent) {
        indentRight();
        String formatted = indent() + toIndent;
        indentLeft();
        return formatted;
    }

    private String indent() {
        return String.join("", Collections.nCopies(indentationLevel, UCharacters.TABULATION));
    }

    private void indentLeft() {
        if (indentationLevel >= 1) {
            --indentationLevel;
        }
    }

    private void indentRight() {
        ++indentationLevel;
    }

}
