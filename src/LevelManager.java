import java.io.*;
import java.util.Hashtable;
import java.util.Scanner;

public class LevelManager {
    /*
    level structure

    competed
    name
    hint
    descr


     */

    public static class Level{
        public final boolean isCompleted;
        public final String name, hint, descr;

        public final boolean isDefault;


        public Level(boolean isCompleted, String name, String hint, String descr, boolean isDefault){
            this.isCompleted = isCompleted;
            this.name = name;
            this.hint = hint;
            this.descr = descr;
            this.isDefault = isDefault;

        }

    }
    public static final String DEFAULT_LEVELS_PREFIX = "default_";
    public static final String CUSTOM_LEVELS_PREFIX = "custom_";


    private static LevelManager instance;

    public static String defaultLevelsFolder;
    public static String customLevelsFolder;


    //getInstance methods
    public static LevelManager getInstance(String defaultLFolder, String customLFolder){
        if(instance == null){
            instance = new LevelManager();
        }
        if(defaultLFolder != null){
            defaultLevelsFolder = defaultLFolder;
        }

        if(customLFolder != null){
            customLevelsFolder = customLFolder;
        }


        return instance;
    }
    public static LevelManager getInstance(){
        return getInstance(null,null);

    }


    //constructors
    private LevelManager(){}


    //other methods
    public void setLevelCompletion(int levelId, boolean isDefaultL, boolean completed){

    }


    //other methods
    public boolean isCompleted(String fileName, boolean isDefaultL){
        File f = getFile(fileName, isDefaultL);
        if(f.exists()){
            try {
                Scanner reader = new Scanner(f);
                boolean result = Integer.parseInt(reader.nextLine()) == 1;
                reader.close();
                return result;

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return false;
    }
    public boolean isCompleted(int id, boolean isDefaultL){
        return isCompleted(getFileName(id, isDefaultL),isDefaultL);
    }
    public boolean isLevel(String fileName, boolean isDefaultL){
        return getFile(fileName, isDefaultL).exists();

    }
    public boolean isLevel(int id, boolean isDefaultL){
        return isLevel(getFileName(id, isDefaultL),isDefaultL);
    }
    public Level getLevel(String fileName, boolean isDefaultL){
        File f = getFile(fileName, isDefaultL);
        Level result = null;
        if(f.exists()){
            try {
                Scanner reader = new Scanner(f);
                //boolean completed = Integer.parseInt(reader.nextLine()) == 1;
                boolean completed = false;
                String name = reader.nextLine();
                String hint = reader.nextLine();
                String descr = "";
                while (reader.hasNext()){
                    descr = descr + reader.nextLine()+"\n";
                }
                descr = descr.substring(0, descr.length()-1);

                reader.close();

                //System.out.println("getLevel debug:");
                //System.out.println(name);
                //System.out.println(hint);
                //System.out.println(descr);
                //System.out.println(completed);

                return new Level(completed,name,hint,descr,true);


            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }
    public Level getLevel(int id, boolean isDefaultL){
        return getLevel(getFileName(id, isDefaultL),isDefaultL);
    }
    /**
     *
     * @param level
     * @return true if the level id was already used by a level: in this case the old level would gert overwritten
     */
    public File writeLevel(int levelId, Level level){
        File f = getFile(getFileName(levelId, level.isDefault),level.isDefault);
        boolean result = false;

        if(!f.exists()){
            result = true;
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            PrintWriter writer = new PrintWriter(f);

            //writer.write(level.isCompleted ? "1\n" : "0\n");
            writer.write(level.name);
            writer.write("\n"+level.hint);
            writer.write("\n"+level.descr);
            writer.close();

        } catch (FileNotFoundException e) {
            return null;
        }


        return f;
    }


    //function-methods
    private void indexLevels(File f, Hashtable hTable){
        RandomAccessFile raf = null;

    }
    private File getFile(String fileName, boolean isDefaultL){
        return new File((isDefaultL ? defaultLevelsFolder : customLevelsFolder) +"/"+ fileName);

    }
    public String getFileName(int id, boolean isDefaultL){
        return (isDefaultL ? DEFAULT_LEVELS_PREFIX : CUSTOM_LEVELS_PREFIX)+id+".txt";

    }
}













