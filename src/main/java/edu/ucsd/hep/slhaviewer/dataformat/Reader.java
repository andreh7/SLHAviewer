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
package edu.ucsd.hep.slhaviewer.dataformat;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * main class to read an SLHA file
 * @author holzner
 */
public class Reader
{
  private final LineNumberReader reader;

  private final SLHAdata data;
  
  //----------------------------------------------------------------------
  static class LineEntry
  {
    /** the original text (for displaying purposes) */
    String origText;
    
    /** text in line after removing comments etc. */
    String line;
    int lineNumber;
    ArrayList<String> parts;
    boolean isEmpty;

    /** @return the given part parsed as an integer */
    public int intPart(int index)
    {
      return Integer.parseInt(parts.get(index));
    }
    
    public double doublePart(int index)
    {
      return Double.parseDouble(parts.get(index));
    }
  }
  
  //----------------------------------------------------------------------
  
  public Reader(InputStream is) throws IOException
  {
    reader = new LineNumberReader(new InputStreamReader(is));
  
    //----------
    // read the stream
    //----------

    String line = null;
    
    List<LineEntry> lines = new ArrayList<LineEntry>();
    
    while ((line = reader.readLine()) != null)
    {
      LineEntry entry = new LineEntry();
      entry.origText = line;
      entry.lineNumber = reader.getLineNumber();
      
      // remove everything after a '#' until the end of line
      // TODO: is there some escaping foreseen ?
      
      int hashPos = line.indexOf('#');
      if (hashPos >= 0)
        line = line.substring(0, hashPos);
      
      // line = line.trim();
      entry.line = line;
      
      // an empty or whitespace only line
      entry.isEmpty = line.trim().isEmpty();
      
      // assume that there are no strings with spaces in them ?!
      if (! entry.isEmpty)
        entry.parts = new ArrayList<String>(Arrays.asList(line.trim().split("\\s+")));
      
      lines.add(entry);
      
    } // loop over all lines
  
    data = new SLHAdata(lines);
    
    //----------
    // now process the lines
    //----------
    processLines(lines);
  
  }

  //----------------------------------------------------------------------

  public SLHAdata getData()
  {
    return data;
  }
  
  //----------------------------------------------------------------------

  /** assumes that comments have been removed already in 'line' */
  private boolean isBlockHeaderLine(String line)
  {
    // it looks like some files (e.g. SModelS example files with cross
    // sections have block body lines which do not have a white space
    // at the beginning of the line but start with numbers..
    //
    // so assume everything which does not start with a letter
    // (and is not an empty line) is a block body line and block
    // header lines start with a letter..
    //
    // if (line.startsWith(" ") || line.startsWith("\t"))
    //

    // find first non-whitespace character in the line

    int len = line.length();

    for (int pos = 0; pos < len; ++pos)
    {
      char ch = line.charAt(pos);
      
      if (Character.isWhitespace(ch))
        continue;
      
      if (Character.isJavaIdentifierStart(ch))
        return true;
      else
        return false;
    }
    
    // looks like it's an empty line
    return false;
  }
  
  //----------------------------------------------------------------------

  /** note that 'lines' is modified */
  private void processLines(List<LineEntry> lines)
  {
    List<LineEntry> thisBlock = null;
    String blockName = null;
    
    while (! lines.isEmpty())
    {
      LineEntry entry = lines.remove(0);
      String line = entry.line.toUpperCase();
    
      //if (entry.isEmpty)
      if (line.trim().isEmpty())
      {
        // commented lines can even appear within a block,
        // so just ignore empty lines and continue
        
        continue;
      }
      
         
      if (! this.isBlockHeaderLine(line))
      {
        // ordinary line
        if (thisBlock == null)
          throw new Error("line " + entry.lineNumber + " not inside block");
        thisBlock.add(entry);
        continue;
      }        
      
      // we have a block header
      if (line.startsWith(("BLOCK ")))
      {
        // process the previous block if defined
        if (thisBlock != null)
          processBlock(blockName, thisBlock);
                
        line = line.substring(6).trim();
        blockName = line.toUpperCase();
        
        thisBlock = new ArrayList<LineEntry>();
        // keep the first line (for consistency reasons)
        thisBlock.add(entry);
        
        continue;
      } 
      
      if (line.startsWith(("DECAY ")))
      {
        // process the previous block if defined
        if (thisBlock != null)
          processBlock(blockName, thisBlock);
                
        blockName = "DECAY";
        
        thisBlock = new ArrayList<LineEntry>();
        thisBlock.add(entry); // keep the first line (it contains particle id and the width)
        
        continue;
      } 
      
      throw new Error("don't know what to do with line " + entry.lineNumber);
    
    } // end of loop over lines
    
    if (thisBlock != null)
    {
      processBlock(blockName, thisBlock);
      blockName = null;
      thisBlock = null;
    }
        
  }
  //----------------------------------------------------------------------

  private void processBlock(String blockName, List<LineEntry> lines)
  {
    
    // this assumes that there is no 'BLOCK DECAY'
    boolean isDecayBlock = blockName.equals("DECAY");

    if (isDecayBlock)
    {
      data.addDecay(new DecayBlock(lines));
      return;
    }
    
    if (blockName.equals("MASS"))
    {
      data.setMasses(new MassBlock(lines));
      return;
    }
    
//    LineEntry firstLine = lines.get(0);
//    if (firstLine.parts.size() < 2)
//      throw new Error("unexpected number of parts");
    
    System.out.println("WARNING: don't know what to do with block " + blockName);
  }
  
  
}
