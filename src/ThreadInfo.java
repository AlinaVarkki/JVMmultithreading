public class ThreadInfo extends Thread{



    public ThreadGroup getRootThreadGroup(){

        Thread currentThread = currentThread();
        ThreadGroup currentThreadGroup = currentThread.getThreadGroup();

        while(currentThreadGroup.getParent() != null){
            currentThreadGroup = currentThreadGroup.getParent();
        }

        return currentThreadGroup;
    }


    

}
