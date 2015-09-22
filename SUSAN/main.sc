import "c_double_handshake";
import "c_queue";
import "susan_thin";
import "stimulus";
import "edge_draw";
import "monitor";
import "detect_edges";
#include "constant.h"

behavior Main(void)
{
  size trigger_size = 1;
  size img_size = IMAGE_SIZE;
  c_double_handshake Trigger(trigger_size);
  c_queue imageBuffer(img_size);
  c_queue imageBuffer2(img_size);
  c_queue detectEdgeOutput(2*img_size);
  c_queue susanThinOutput(img_size);
  c_queue edgeDrawOutput(img_size);
  stimulus myStimulus(imageBuffer, Trigger, imageBuffer2); 
  detectedges mydetectEdges(imageBuffer, Trigger, detectEdgeOutput);
  susan_thin mySusanThin(detectEdgeOutput, susanThinOutput); 
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
