# Lab report template

Document template suitable for use as a latex master-file for lab
reports in University of Turku Department of Future Technologies. 

Compatible with: sharelatex, pdflatex, xelatex, lyx

## How to use?

Want to write a lab report?

### LyX version

 - install LyX (https://www.lyx.org/)
 - install a LaTeX distribution
   - MacTeX (Mac): http://www.tug.org/mactex/
   - MikTeX (Windows): https://miktex.org/
   - TeXLive (Linux/BSD): sudo apt-get install / pacman -S / ...
   - etc.
  - clone this repository
  - start with 'exercise.lyx'

### ShareLaTeX

 - download this repository as a zip
 - go to https://tex.soft.utu.fi
 - start a new ShareLaTeX project, upload the zip file as a doc template
 
### Ordinary LaTeX 

 - install a LaTeX distribution (see above)
 - clone the repository / download as zip
 - edit 'exercise.tex' (use an editor with utf8 support)
 - compile with: latexmk -xelatex exercise.tex

## Bugs?

Start a new issue / merge request.

## Features

Examples of using the standard document features of this class are presented
in exercise.tex / exercise.lyx. You can also write Java code between
\begin{javacode} and \end{javacode}. See the utuftlabreport.cls for more
details.

## How to learn LaTeX?

Traditionally the best places to learn (La)TeX are probably the manual
pages for each package
 * http://www.ctan.org/ 
 * http://www.ctan.org/tex-archive/info/lshort/english/lshort.pdf
