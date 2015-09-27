import "c_double_handshake";
//import "c_queue";
import "c_image_queue";
import "c_intR_queue";
import "susan_thin";
import "stimulus";
import "edge_draw";
import "monitor";
import "detect_edges";
#include "constant.hh"


behavior susan(i_receiver Trigger, i_image_receiver imageReadOut1, i_image_receiver imageReadOut2,  i_image_sender imageSusanOut)
{
  //constants 
  const unsigned long  trigger_size = 1;
  const unsigned long  img_size = IMAGE_SIZE;
  const unsigned long  img_size4 = 4*IMAGE_SIZE;
  
  //internal channels 
  c_intR_queue detectEdgeOutputR(img_size4);
  c_image_queue detectEdgeOutputMid (img_size);
  c_image_queue susanThinOutput(img_size);
  
  //behavior instantiation 
  detectedges mydetectEdges(imageReadOut1, detectEdgeOutputR, detectEdgeOutputMid);
  susan_thin mySusanThin(detectEdgeOutputR, detectEdgeOutputMid,susanThinOutput); 
  edge_draw myEdgeDraw(susanThinOutput, imageReadOut2, imageSusanOut);
  
  void main(void) {
      int flag;  
      Trigger.receive(&flag, 1);
      par {
          mydetectEdges;
          mySusanThin;
          myEdgeDraw;
      }
  }
};
