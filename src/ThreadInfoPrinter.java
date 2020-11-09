public class ThreadInfoPrinter extends Thread{

    //method that prints info about a ThreadGroup and its threads
    public void printInfo() {
        ThreadGroup[] threadGroups = getAllThreadGroups();

        for(ThreadGroup a: threadGroups){
            System.out.println(a.toString());

        }
    }

    public ThreadGroup getRootThreadGroup(){
        ThreadGroup currentThreadGroup = currentThread().getThreadGroup();

        while(currentThreadGroup.getParent() != null){
            currentThreadGroup = currentThreadGroup.getParent();
        }

        return currentThreadGroup;
    }

    public ThreadGroup[] getThreadGroupsAfterRoot(){
        ThreadGroup root = getRootThreadGroup();
        ThreadGroup[] threadGroupsAfterRoot = new ThreadGroup[root.activeGroupCount()];

        //fill list with thread groups coming after root (System ThreadGroup)
        root.enumerate(threadGroupsAfterRoot);

        return threadGroupsAfterRoot;
    }

    //create new ThreadGroups list that includes root
    public ThreadGroup[] getAllThreadGroups(){
        ThreadGroup[]threadGroupsAfterRoot = getThreadGroupsAfterRoot();
        ThreadGroup[] allThreadGroups = new ThreadGroup[threadGroupsAfterRoot.length + 1];

        allThreadGroups[0] = getRootThreadGroup();

        for(int i = 1; i < threadGroupsAfterRoot.length + 1; i++){
            allThreadGroups[i] = threadGroupsAfterRoot[i-1];
        }
        
        return allThreadGroups;
    }

}