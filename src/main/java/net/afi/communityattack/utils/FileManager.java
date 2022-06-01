package net.afi.communityattack.utils;

public class FileManager {

    private CFGFile voteFile;
    private CFGFile prefixFile;
    private CFGFile patentFile;

    public FileManager(String dataPath) {

        voteFile = new CFGFile(dataPath + "/", "vote.yml");
        voteFileSetup();

        prefixFile = new CFGFile(dataPath + "/", "prefixes.yml");
        prefixFileSetup();

        patentFile = new CFGFile(dataPath + "/", "patents.yml");
    }

    public CFGFile getVoteFile() {
        return voteFile;
    }

    private void voteFileSetup() {

        if(voteFile.getObject("electionopen") == null)voteFile.setValue("electionopen", false);
        if(voteFile.getObject("registryopen") == null)voteFile.setValue("registryopen", false);
    }

    public CFGFile getPrefixFile() {
        return prefixFile;
    }

    private void prefixFileSetup() {

        if(prefixFile.getObject("mayor") == null)prefixFile.setValue("mayor", "&6&lB &7| ");

        if(prefixFile.getObject("default") == null)prefixFile.setValue("default", "&7");
    }

    public CFGFile getPatentFile() {
        return patentFile;
    }
}
