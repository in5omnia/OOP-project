package prr.clients;

import prr.terminals.communication.Text;
import prr.terminals.communication.Video;
import prr.terminals.communication.Voice;

public class DetectCommunicationVisitor {
    public void visit(Text com, Level level){
        level.detectCommunication(com);
    }

    public void visit(Voice com, Level level){
        level.detectCommunication(com);
    }

    public void visit(Video com, Level level){
        level.detectCommunication(com);
    }
}
