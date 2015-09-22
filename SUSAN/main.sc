#include "constant.h"
import "c_double_handshake";
import "c_queue";
import "susan_thin";
import "stimulus";
import "edge_draw";
import "monitor";
import "detect_edges";

behavior Main(void)
{
  c_double_handshake Trigger;
  c_queue imageBuffer;
  c_queue imageBuffer2;
  c_queue detectEdgeOutput;
  c_queue susanThinOutput;
  c_queue edgeDrawOutput;
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
