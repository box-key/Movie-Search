## Who am I?

Name: Kei Nemoto

## How to run it?

Important :  project4.zip and mysql-connector-java-8.0.12.jar must be in same folder

then in the command line, use following instruction to run the program

					java -jar project4.zip


## How to run it?

This program gives answer for each question.
You can start this program by running main function. 

In the main.py, please pass the parameters in the following order:
1) Path to directory that contains training files of class 1
2) Path to directory that contains training files of class 2
3) Path to directory that contains testing files of class 1
4) Path to directory that contains testing files of class 2
5) Path to output parameter files
6) Path to output prediction files
7) Path to vocabulary file

## How many minutes it takes?
I run this program on a Windows 10 laptop computer with Intel Core i7 @ 2.30GHz and 8 GB RAM, and it took about 30 minutes to finish the whole program


## Description of files

main.py: The root of this project
Question1.py: Answer question of the first part.
NB.py: Answer questions of the second part. 
NB_utilities.py: Contains functions used for Naive Bayes. 
SupervisingClass.py: This class encapsulates the data of supervising class such as classname, number of files associated with the class, list of words associated with the class, and list of words in BOW feature.
Vocabulary.py: It contains sorted vocabulary list and its size. Other class can access through the vocabulary list through getAt method.
io_utilities.py: Contains function related to file I/O.
pre_process.py: Provides functions for preprocessing given documetns. It contains regular preprocessing method and preprocessing for stop word feature.

## External Packages used in this program
import math for calculating log
import os, glob for file io
import time for timing the program
import collections for counting words in input
import bisect for searching word in a list

