import java.util.Random;
import java.util.Scanner;

public class youthpastor {
    public static void main(String[] args) {
        try (Scanner kybd = new Scanner(System.in)) {
            Random random = new Random();

            String[] fearVerses = {
                "Isaiah 41:10 — 'Do not fear, for I am with you; do not be dismayed, for I am your God. I will strengthen you and help you.'",
                "Psalm 34:4 — 'I sought the Lord, and He answered me; He delivered me from all my fears.'",
                "1 Peter 5:7 — 'Cast all your anxiety on Him because He cares for you.'"
            };

            String[] strengthVerses = {
                "Philippians 4:13 — 'I can do all things through Christ who strengthens me.'",
                "Joshua 1:9 — 'Be strong and courageous. Do not be afraid; do not be discouraged, for the Lord your God will be with you wherever you go.'",
                "Psalm 46:10 — 'Be still, and know that I am God.'"
            };

            String[] guidanceVerses = {
                "Proverbs 3:5-6 — 'Trust in the Lord with all your heart and lean not on your own understanding; in all your ways submit to Him, and He will make your paths straight.'",
                "Jeremiah 29:11 — 'For I know the plans I have for you,' declares the Lord, 'plans to prosper you and not to harm you, plans to give you hope and a future.'",
                "Romans 8:28 — 'And we know that in all things God works for the good of those who love Him, who have been called according to His purpose.'"
            };

            String[] hopeVerses = {
                "Romans 15:13 — 'May the God of hope fill you with all joy and peace as you trust in Him.'",
                "Psalm 27:1 — 'The Lord is my light and my salvation—whom shall I fear? The Lord is the stronghold of my life—of whom shall I be afraid?'",
                "Lamentations 3:22-23 — 'His mercies are new every morning; great is your faithfulness.'"
            };

            String[] joyVerses = {
                "Nehemiah 8:10 — 'The joy of the Lord is your strength.'",
                "Philippians 4:4 — 'Rejoice in the Lord always. I will say it again: Rejoice!'",
                "Psalm 100:4 — 'Enter His gates with thanksgiving and His courts with praise; give thanks to Him and praise His name.'"
            };

            System.out.println("\n====================================");
            System.out.println("   YOUTH PASTOR SCRIPTURE TIME");
            System.out.println("====================================\n");

            System.out.println("Hey friend, let me share a little truth from Scripture with you.");
            System.out.println("Do you want me to share God’s word with you? (yes/no)");
            String continueChoice = kybd.nextLine();

            if (!continueChoice.equalsIgnoreCase("yes")) {
                System.out.println("That’s okay. Remember, God is still with you. See you next time.");
                return;
            }

            System.out.println("\nWhat are you feeling right now?");
            System.out.println("1) Afraid or worried");
            System.out.println("2) Weak or needing strength");
            System.out.println("3) Unsure or needing direction");
            System.out.println("4) Discouraged or needing hope");
            System.out.println("5) Joyful or thankful");
            System.out.print("Enter 1-5: ");
            int moodChoice = kybd.nextInt();
            kybd.nextLine();

            String chosenVerse;
            String responseType;

            switch (moodChoice) {
                case 1 -> {
                    chosenVerse = fearVerses[random.nextInt(fearVerses.length)];
                    responseType = "afraid or worried";
                }
                case 2 -> {
                    chosenVerse = strengthVerses[random.nextInt(strengthVerses.length)];
                    responseType = "weak or in need of strength";
                }
                case 3 -> {
                    chosenVerse = guidanceVerses[random.nextInt(guidanceVerses.length)];
                    responseType = "unsure or needing direction";
                }
                case 4 -> {
                    chosenVerse = hopeVerses[random.nextInt(hopeVerses.length)];
                    responseType = "discouraged or in need of hope";
                }
                case 5 -> {
                    chosenVerse = joyVerses[random.nextInt(joyVerses.length)];
                    responseType = "joyful and thankful";
                }
                default -> {
                    System.out.println("\nThat’s okay — let’s pick a verse anyway.");
                    chosenVerse = guidanceVerses[random.nextInt(guidanceVerses.length)];
                    responseType = "seeking guidance";
                }
            }

            System.out.println("\nYou picked: " + responseType);
            System.out.println("\nHere is your verse:");
            System.out.println(chosenVerse);
            System.out.println("\nPastor’s encouragement: God sees you, He is with you, and He is working in your life right now.");
            System.out.println("\nKeep walking with Jesus. He is faithful and He is near.");

            System.out.println("\n====================================");
            System.out.println("      GO IN PEACE AND GRACE");
            System.out.println("====================================");
        }
    }
}
