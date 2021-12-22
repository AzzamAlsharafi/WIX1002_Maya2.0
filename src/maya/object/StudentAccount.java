package maya.object;

import java.io.IOException;
import java.io.ObjectOutputStream;
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

//    MUET, IELTS,
//    TOEFL Paper–Based Test, TOEFL Computer–Based Test, TOEFL Internet–Based Test,
//    PTE (Academic), FCE, GCE A Level (English), IGCSE/GCSE (English)
    public static int calculateMUETBand(int test, String scoreString){
        try {
            switch (test){
                case 1 -> { //MUET
                    return Integer.parseInt(scoreString);
                }
                case 2 -> { // IELTS
                    double score = Double.parseDouble(scoreString);
                    if(score >= 6.5){
                        return 5;
                    }else if(score >= 5.5){
                        return 4;
                    }else if(score >= 4.5){
                        return 3;
                    }else if(score >= 4.0){
                        return 2;
                    }
                }
                case 3 -> { // TOEFL Paper–Based Test
                    int score = Integer.parseInt(scoreString);
                    if(score >= 550){
                        return 5;
                    }else if(score >= 513){
                        return 4;
                    }else if(score >= 477){
                        return 3;
                    }else if(score >= 437){
                        return 2;
                    }
                }
                case 4 -> { // TOEFL Computer–Based Test
                    int score = Integer.parseInt(scoreString);
                    if(score >= 213){
                        return 5;
                    }else if(score >= 183){
                        return 4;
                    }else if(score >= 153){
                        return 3;
                    }else if(score >= 123){
                        return 2;
                    }
                }
                case 5 -> { // TOEFL Internet–Based Test
                    int score = Integer.parseInt(scoreString);
                    if(score >= 79){
                        return 5;
                    }else if(score >= 65){
                        return 4;
                    }else if(score >= 53){
                        return 3;
                    }else if(score >= 41){
                        return 2;
                    }
                }
                case 6 -> { // PTE (Academic)
                    int score = Integer.parseInt(scoreString);
                    if(score >= 58){
                        return 5;
                    }else if(score >= 42){
                        return 4;
                    }else if(score >= 29){
                        return 3;
                    }else if(score >= 10){
                        return 2;
                    }
                }
                case 7 -> { // FCE
                    if(scoreString.equalsIgnoreCase("A")){
                        return 5;
                    } else if(scoreString.equalsIgnoreCase("B") || scoreString.equalsIgnoreCase("C")){
                        return 4;
                    }
                }
                case 8 -> { // GCE A Level (English)
                    if(scoreString.equalsIgnoreCase("A") || scoreString.equalsIgnoreCase("B")){
                        return 5;
                    } else if(scoreString.equalsIgnoreCase("C")){
                        return 4;
                    }
                }
                case 9 -> { // IGCSE/GCSE (English)
                    if(scoreString.equalsIgnoreCase("A") || scoreString.equalsIgnoreCase("B")
                            || scoreString.equalsIgnoreCase("C")) {
                        return 4;
                    }
                }
            }
        } catch (NumberFormatException e){
            return -1; // Invalid score
        }

        return 0;
    }

    public StudentAccount(String siswaMail, String matricNumber, String password, String fullName, List<String> occurrences, int programme, int  englishBand, int citizenship) {
        super(siswaMail, matricNumber, password, fullName, occurrences);
        this.programme = programme;
        this.englishBand = englishBand;
        this.citizenship = citizenship;
    }

    @Override
    public void storeAccount(ObjectOutputStream out) throws IOException {
        out.writeUTF("Student");
        super.storeAccount(out);
        out.writeInt(programme);
        out.writeInt(englishBand);
        out.writeInt(citizenship);
    }
}
