# progetto_LAM



### SE HO TEMPO:

- Quando si GIRA LO SCHERMO in orizzontale l'activity corrente viene ricaricata da zero (e non semplicemente girata) e quindi: dalla torta passa all'istogramma, dai filtri home passa alla home normale, altro?

- Quando si inseriscono i dati del FORM di creazione o modifica task fare in modo che vengano visualizzati (altrimenti sembra che non si riesca ad impostare nulla)

- Sostituisci tutte le STRINGHE nei file java con 'getResources().getString(R.string.string_name)'

- I FILTRI delle notifiche funzionano con app aperta o chiusa, ma non se viene chiusa e riaperta (i filtri vengono così azzerati).
  Soluzione: Aggiungere campo 'bool notif_on' ai task per capire se vanno notificati o meno

- Quando dalla notifica fai una delle due azioni le activity vengono SOVRAPPOSTE

- Se si clicca su 'POSPONI' nella notifica poi non si può annullare, l'unico modo è segnare un nuovo orario

- Prima di usare la funzione getTask(id) dovrei sempre fare if(containsTask(id)) per essere sicuro di non incappare in un errore a runtime



### TEST:

Test controllo mostrare NOTIFICA:

  + app sempre aperta
  + andato nella home e poi tornato nell'app
  + andato nella home
  + app chiusa e poi riaperta
  + app chiusa
  + telefono bloccato (con l'app ancora aperta)
  + telefono spento e poi app riaperta
  + telefono spento e riacceso (ma senza aprire l'app)
  + telefono spento
