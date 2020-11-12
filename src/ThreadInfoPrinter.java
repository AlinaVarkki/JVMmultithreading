import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ThreadInfoPrinter extends Thread{

    private int indent = 0;
    //method that prints info about a ThreadGroup and its threads
    public void printInfo(int format) {
        ThreadGroup[] threadGroups = getAllThreadGroups();
        Thread[] threads = getAllThreads(getRootThreadGroup());
        switch(format){
            case 0:
                // Print in hierarchy format, i.e. print thread group with all child threads/thread groups beneath it
                printHierarchy(threadGroups[0],threadGroups,threads, indent);
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

    public void searchThreadByName(String name){
        Thread[] threads = getAllThreads(getRootThreadGroup());

        for(Thread t: threads){
            if(t.getName().equals(name)){
                printIndividualThreadInfo(t, 0);
            }
        }
    }

    //printing info every 10 seconds
    public void printInfoUpdating(){
        Timer t = new Timer();

        t.schedule(new TimerTask() {
            @Override
            public void run() {
                //adding date to see when it got updated
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date));
                System.out.println("Hierarchy Format:\n");

                printInfo(0);

            }
        }, 0, 10000);

    }

    private void printHierarchy(ThreadGroup currentGroup, ThreadGroup[] threadGroups, Thread[] threads, int indent){
        //Display current ThreadGroup
        System.out.println(currentGroup.toString().indent(indent));

        //Display current ThreadGroups threads
        for(Thread t: threads){
            if(t.getThreadGroup().equals(currentGroup)){
                printIndividualThreadInfo(t, indent);
            }
        }

        indent = indent + 10;
        //Display current ThreadGroups child groups and their associated threads
        for(ThreadGroup g: threadGroups){
            if(!g.equals(getRootThreadGroup())){
                if(g.getParent().equals(currentGroup)){
                    printHierarchy(g,threadGroups,threads, indent);
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

    private void printIndividualThreadInfo(Thread thread, int indent){
        String threadInfo = "Thread name: "+thread.getName() + "\n" +  " Thread identifier: " + thread.getId() + "\n" +  " Thread stage: " + thread.getState() + "\n" +  " Thread priority: " + thread.getPriority() + "\n" + " This thread is daemon: " + thread.isDaemon();
        System.out.println(threadInfo.indent(indent + 2));
    }
}