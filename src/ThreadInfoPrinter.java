public class ThreadInfoPrinter extends Thread{

    //method that prints info about a ThreadGroup and its threads
    public void printInfo(int format) {
        ThreadGroup[] threadGroups = getAllThreadGroups();
        Thread[] threads = getAllThreads(getRootThreadGroup());
        switch(format){
            case 0:
                // Print in hierarchy format, i.e. print thread group with all child threads/thread groups beneath it
                printHierarchy(threadGroups[0],"",threadGroups,threads);
                break;
            case 1:
                // Print in Listed format, i.e. print all thread groups, then all threads
                for(ThreadGroup a: threadGroups){
                    System.out.println(a.toString());
                }
                for(Thread t: threads){
                    System.out.println(t.toString());
                }
                break;
            default:
                System.err.println("Warning: Invalid format value!");


        }

    }

    private void printHierarchy(ThreadGroup currentGroup, String indent, ThreadGroup[] threadGroups, Thread[] threads){
        //Display current ThreadGroup
        System.out.println(indent + currentGroup.toString());
        indent += "\t";

        //Display current ThreadGroups threads
        for(Thread t: threads){
            if(t.getThreadGroup().equals(currentGroup)){
                System.out.println(indent + t.toString());
            }
        }

        //Display current ThreadGroups child groups and their associated threads
        for(ThreadGroup g: threadGroups){
            if(!g.equals(getRootThreadGroup())){
                if(g.getParent().equals(currentGroup)){
                    printHierarchy(g,indent,threadGroups,threads);
                }
            }

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
        ThreadGroup[] threadGroupsAfterRoot = getThreadGroupsAfterRoot();
        ThreadGroup[] allThreadGroups = new ThreadGroup[threadGroupsAfterRoot.length + 1];

        allThreadGroups[0] = getRootThreadGroup();

        for(int i = 1; i < threadGroupsAfterRoot.length + 1; i++){
            allThreadGroups[i] = threadGroupsAfterRoot[i-1];
        }
        
        return allThreadGroups;
    }

    public Thread[] getAllThreads(ThreadGroup rootThreadGroup){
        Thread[] threads = new Thread[rootThreadGroup.activeCount()];
        rootThreadGroup.enumerate(threads);
        return threads;
    }
}