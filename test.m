clc;
close all;
clear all;


numbers = [3 3 3 5 4 3 3 1 5 5 3 4 3 2 3 5 4 0 0 1 3 5 1 5 4 3 4 3 4 3 4 2 3 5 5 5 5 3 2 4 2 5 3 3 4 1 3 1 0 5 3 5 4 1 4 2 5 3 4 5 5 4 5 4 0 5 4 2 2 5 5 5 3 5 4 5 3 3 5 5 1 2 5 5 5 5 5 4 3 5 4 4 4 4 1 2 5 4 4 2 1 3 5 1 2 3 3 5 5 5 3 5 4 4 3 4 4 4 3 2 5 1 5 1 3 1 5 4 3 4 2 5 4 5 5 1 1 3 3 5 5 4 5 5 2 1 5 4 5 4 4 2 5 4 4 4 1 5 5 5 2 3 3 5 3 5 5 4 1 4 4 2 5 5 2 3 2 5 3 5 4 2 3 4 4 0 5 5 5 2 5 0 4 1 1 3 0 1 3 4 5 3 5 3 1 5 4 3 4 4 5 4 4 5 3 3 3 5 2 3 3 2 4 5 4 3 4 1 5 5 5 5 4 3 4 5 1 0 0 2 2 2 2 2 2 4 4 3 5 5 1 3 4 5 2 5 3 2 4 3 4 4 5 4 3 5 2 3 3 3 3 4 3 2 3 4 3 1 5 3 0 0 4 4 4 2 2 2 5 3 5 5 3 4 1 5 5 5 2 3 ];

% Getting the range.
m = min(numbers);
M = max(numbers);
all = m:M;
N = length(all);

count = zeros(1,N);

for i = all
  count(i+1) = length(find(numbers == i));
end

p = count/sum(count)
t = (1:6)/sum(1:6)

e = sum((p-t).^2)

disp('Done')