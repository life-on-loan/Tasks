// Thread 1
a = 123;        // Обычная запись
b = true;       // Volatile запись (барьер)

// Thread 2
while (!b) {}   // Volatile чтение (барьер)
System.out.println(a); // Гарантированно увидит a = 123