/*
 * Copyright 2015 University of California, San Diego.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.ucsd.hep.slhaviewer.view;

import edu.ucsd.hep.slhaviewer.dataformat.Reader;
import edu.ucsd.hep.slhaviewer.dataformat.SLHAdata;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author holzner
 */
public class MassGraphPanelTest 
{
  public static void main(String argv[]) throws IOException
  {
    String fname = "testdata/susyhit_slha-pmssm-01.out";
    
    Reader reader = new Reader(new FileInputStream(fname));

    SLHAdata slhaData = reader.getData();
    MassGraphPanel instance = new MassGraphPanel();
    instance.setParticles(slhaData);
    
    JFrame frame = new JFrame(fname);
    frame.setSize(500,500);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(instance);
    
    frame.setVisible(true);
    
  }
  
}
