Simple API that serves Kortepohja's bus times.


Idea of this application is to provide bus information for ESP32 board that
show information in waveshare e-ink monitor.

Run application and go to `http://localhost:300` and you get something like this:

```
     BUSSIAIKATAULUT

   22:46|18K 00:16|18
   23:08|25  00:28|25
   23:16|18  05:28|25
   23:28|25  05:41|18K
```

**run**
lein run

**build jar**
lein uberjar
