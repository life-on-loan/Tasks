private int a = 0;
private volatile boolean b = false;

// Thread1
a=123;
b=true;

// Thread2
while(!b) {}
System.out.println(a);

// TODO: Можно ли гарантировать результат вывода на экран переменной a?