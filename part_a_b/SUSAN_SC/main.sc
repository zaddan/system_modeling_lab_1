import "c_double_handshake";
import "c_queue";
import "susan_thin";
import "stimulus";
import "edge_draw";
import "monitor";
import "detect_edges";
#include "constant.hh"

behavior Main(void)
{
  const unsigned long  trigger_size = 1;
  const unsigned long  img_size = IMAGE_SIZE;
  const unsigned long  img_size4 = 4*IMAGE_SIZE;
  c_double_handshake Trigger;
  c_queue imageBuffer(img_size);
  c_queue imageBuffer2(img_size);
  c_queue detectEdgeOutputR(img_size4);
  c_queue detectEdgeOutputMid (img_size);
  
  c_queue susanThinOutput(img_size);
  c_queue edgeDrawOutput(img_size);
  stimulus myStimulus(imageBuffer, Trigger, imageBuffer2); 
  detectedges mydetectEdges(imageBuffer, Trigger, detectEdgeOutputR, detectEdgeOutputMid);
  susan_thin mySusanThin(detectEdgeOutputR, detectEdgeOutputMid,susanThinOutput); 
  edge_draw myEdgeDraw(susanThinOutput, imageBuffer2, edgeDrawOutput);
  monitor myMonitor(edgeDrawOutput);  
  
  int main(void) {
      printf("start getting the image\n");
      par {
          myStimulus;
          mydetectEdges;
          mySusanThin;
          myEdgeDraw;
          myMonitor;
      }
      return(0);
  }
};
