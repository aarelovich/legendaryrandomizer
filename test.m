clc;
close all;
clear all;


numbers = [2 1 2 5 4 0 3 0 2 3 4 5 3 5 4 3 0 1 3 1 1 4 5 2 4 5 1 0 5 3 0 4 0 3 3 2 3 1 2 3 3 0 5 5 1 3 3 3 0 0 4 0 5 2 3 5 5 1 1 0 5 0 3 5 3 4 5 2 3 0 5 4 4 1 5 5 3 5 3 4 4 4 3 2 3 5 1 5 1 0 2 1 4 4 3 2 5 5 2 2 0 5 4 1 5 4 4 2 0 3 1 3 2 5 0 5 0 4 5 3 0 4 0 2 5 5 0 3 3 5 2 2 3 1 1 5 4 4 3 5 3 3 2 5 2 4 4 5 4 5 2 5 1 1 4 1 3 5 5 4 5 3 3 4 1 1 5 5 2 4 4 2 4 0 5 4 5 5 1 2 4 5 4 3 5 3 3 3 2 5 3 4 1 3 5 2 2 5 2 4 1 0 3 2 4 5 4 5 4 4 5 4 4 2 5 0 4 4 0 2 3 3 2 2 1 4 5 5 4 5 5 3 3 2 3 5 5 4 4 1 3 3 1 3 3 4 4 2 5 2 3 5 4 3 2 1 5 4 3 4 0 5 2 5 1 4 0 2 2 3 2 3 5 3 0 4 1 3 5 3 0 1 2 4 5 4 1 0 4 4 5 4 5 3 3 5 4 2 2 2];

% Getting the range.
m = min(numbers);
M = max(numbers);
all = m:M;

count = zeros(1,length(all));

for i = all
  count(i+1) = length(find(numbers == i));
end

p = count/sum(count)

%% Test.
t = (1:6)/sum(1:6);

[X Y] = meshgrid(t,t);
c = abs(X-Y);
c(c == 0) = 1e6;
d = min(min(c))
K = 100/d

disp('Done')