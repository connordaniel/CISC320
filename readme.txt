The way my program works is that it, first, reads in a file through STDIN.
After that, the text file is put into an array, which is then mapped from strings to ints.
The limit is given to be the first element in the array, since that is the first number that was in the text file.
There is then a for loop going from 1, the second element in the array (becasue the first element is how many we are 
going to read in), and up to whatever number was in the first element. Because this loop runs iteratively through the 
array, this program has a time complexity of O(n), for each element in the text file/array.