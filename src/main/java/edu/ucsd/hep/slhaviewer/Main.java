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

package edu.ucsd.hep.slhaviewer;

import edu.ucsd.hep.slhaviewer.dataformat.Reader;
import edu.ucsd.hep.slhaviewer.view.MainWindow;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The main class.
 */
public class Main 
{
  //----------------------------------------------------------------------

  public static void main(String[] argv) throws IOException
  {
    if (argv.length != 1)
    {
      System.err.println("must specify exactly one slha file");
      System.exit(1);
    }
 
    String fname = argv[0];
    
    // open the file
    Reader reader = new Reader(new FileInputStream(fname));
    
    MainWindow window = new MainWindow(fname, reader.getData());
    window.setVisible(true);
  }
}
