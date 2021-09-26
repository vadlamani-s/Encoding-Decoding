# Encoding-Decoding
The code encodes and decodes sequence of characters given the coding symbols to be used.
1. Encoder: the encoder method has been created to encode a given text. The method has been implemented 
fully and tested thoroughly.
2. Decoder: the decoder method has been created to decode a given text. The method has been implemented 
fully and tested thoroughly.
3. Map Generator: the method has been created to generate a prefix encoding map. The method has been 
implemented fully and tested thoroughly.

## How to run the program:
The program can be run by using the JAR file present in the ZIP folder.
1. The Zip folder contains 3 folders. 
2. Navigate to the 'res' folder which contains the HW4.jar file.
3. Using terminal run the HW4.jar file by navigating to the folder where the current file is present
 and using the following command, "java -jar HW4.jar".

Note: Make sure JAVA class path has been added to the environment variables.

## Design:
1. An interface has been created to have common functionalities namely encoding and decoding methods.
2. There is CodeImpl class implementing the interface which has the implementation of the encoding 
and decoding methods.
3. The prefix encoding/map builder method was initially considered to be an instance of the CodeImpl
class but this would make it difficult to generate only a map given text and code symbols. Inorder 
to make it accessible, without creating an instance of the class the method was made static.
4. Two classes, intermediate node and leaf node have been created to build a prefix tree for 
decoding purposes. The leaf node keeps track of the characters.
5. An interface has been created for holding the common functionalities of the intermediate and 
leaf nodes of the tree.
6. The code symbols have been designed to be generic and have no exceptions on the characters that 
could be used.
7. The program will be terminated for any incorrect options entered while running the driver class.

## Assumptions:
1. If no code symbols are provided for generating the prefix map, by default it is not considered to
be binary encoding, and an exception is thrown.
2. It is assumed that the map entered by the user is of valid format and no further checks have been 
made to check the validity.
3. The format of the map provided by the user is fixed and has to be as follows,
{a=10,b=100,c=01,d=010} where, the string to left of '=' is the key/character, and the right of '='
is the corresponding encoding.
4. If a particular character to be decoded doesn't already exist in the prefix tree, an exception is
thrown.
5. The code symbol needs to have at least 2 characters to form a prefix map. An exception
is thrown otherwise.

## Program Features:
1. Encoder: the encoder method has been created to encode a given text. The method has been implemented 
fully and tested thoroughly.
2. Decoder: the decoder method has been created to decode a given text. The method has been implemented 
fully and tested thoroughly.
3. Map Generator: the method has been created to generate a prefix encoding map. The method has been 
implemented fully and tested thoroughly.
4. Driver: the driver class fulfills all the requirements mentioned in the home work document. 
A prefix encoding map is generated given a text and code symbols. It can read the map from a file or
from the console. It can generate an encoding and decoding for a text based on the prefix map either
by reading from a file or console and writing back to a file or console.
All the methods have been tested.
