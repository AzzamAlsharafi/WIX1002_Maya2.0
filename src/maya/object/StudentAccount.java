package maya.object;

import java.util.List;

public class StudentAccount extends Account{
    final int programme;
    final int englishBand;
    final int citizenship;

    static final int PROGRAMME_CSN = 0;
    static final int PROGRAMME_AI = 1;
    static final int PROGRAMME_IS = 2;
    static final int PROGRAMME_SE = 3;
    static final int PROGRAMME_MM = 4;
    static final int PROGRAMME_DS = 5;

    static final int CITIZENSHIP_MALAYSIAN = 0;
    static final int CITIZENSHIP_NON_MALAYSIAN = 1;

    public int getProgramme() {
        return programme;
    }

    public int getEnglishBand() {
        return englishBand;
    }

    public int getCitizenship() {
        return citizenship;
    }

    public StudentAccount(String siswaMail, String matricNumber, String password, String fullName, List<String> occurrences, int programme, int  englishBand, int citizenship) {
        super(siswaMail, matricNumber, password, fullName, occurrences);
        this.programme = programme;
        this.englishBand = englishBand;
        this.citizenship = citizenship;
    }
}
