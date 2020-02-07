# progetto_LAM



### DA FARE:

- Fai SCREENSHOT dell'app da inserire nella relazione

- Aggiungi il mio NOME alla relazione

- Prova a visualizzare tanti elementi in cronologia

- Quando si clicca sui bottoni delle notifiche non avviene l'azione ma si blocca sulla home



### MANCA:

- Relazione + slides

- (Credits del progetto)



### CONTROLLARE:

- Se si gira il cellulare in orizzontale l'app funziona ugualmente?

- Tutti i task relativi a giorni/ore passate non devono essere mostrati nella home



### SE HO TEMPO:

- CRONOLOGIA attività completate

- Use le CARD nella home (e magari anche nelle altre activity), al posto di una semplice serie di stringhe

- Quando si GIRA LO SCHERMO in orizzontale l'activity corrente viene ricaricata da zero (e non semplicemente girata) e quindi: dalla torta passa all'istogramma, dai filtri home passa alla home normale, altro?

- Quando si inseriscono i dati del FORM di creazione o modifica task fare in modo che vengano visualizzati (altrimenti sembra che non si riesca ad impostare nulla)

- Grafico a TORTA per visualizzare i vari stati dei task (pending, ongoing, completed)

- Sostituisci tutte le STRINGHE nei file java con 'getString(R.string.string_name)'

- I FILTRI delle notifiche funzionano con app aperta o chiusa, ma non se viene chiusa e riaperta (i filtri vengono così azzerati).
  Soluzione: Aggiungere campo 'bool notif_on' ai task per capire se vanno notificati o meno

- Quando dalla notifica fai una delle due azioni le activity vengono SOVRAPPOSTE

- Se si clicca su 'POSPONI' nella notifica poi non si può annullare, l'unico modo è segnare un nuovo orario

- I PULSANTI delle notifiche non funzionano se l'app è chiusa

- Nuovo GRAFICO che comprenda sia le attivià passate che quelle in programma

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
