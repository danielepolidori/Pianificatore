# progetto_LAM



### DA FARE:

- Quando dalla notifica fai una delle due azioni le activity vengono SOVRAPPOSTE invece di fare finish()

- Cosa succede se si cliccano le azioni delle notifiche mentre la home è in visualizzazione con FILTRI?

- Se si clicca su 'POSPONI' nella notifica poi non si può annullare, l'unico modo è segnare un nuovo orario

- Pulsante AZZERA filtri delle notifiche nell'alert

- DESCRIZIONE aggiunta filtri notifiche nell'alert più corta

- Le notifiche delle attività create DOPO l'impostazione del filtro notifiche non vengono filtrate (non rispettano il filtro)



### SUCCESSIVAMENTE:

- Rendi la HOME più carina

- CRONOLOGIA (a quel punto non possono più essere modificate)

- Nuovo GRAFICO che comprenda sia le attivià passate che quelle in programma



### MANCA:

- Visualizzare le notifiche con filtri

- Relazione + slides

- (Credits del progetto)



### CONTROLLARE:

- Se si gira il cellulare in orizzontale l'app funziona ugualmente?

- Tutti i task relativi a giorni/ore passate non devono essere mostrati nella home



### IDEE:

- CRONOLOGIA attività completate

- Use le CARD nella home (e magari anche nelle altre activity), al posto di una semplice serie di stringhe



### SE HO TEMPO:

- Nell'activity dei grafici quando si GIRA LO SCHERMO in orizzontale l'activity viene ricaricata da zero (e non semplicemente girata) e quindi dalla torta passa all'istogramma

- Quando si inseriscono i dati del FORM di creazione o modifica task fare in modo che vengano visualizzati (altrimenti sembra che non si riesca ad impostare nulla)

- Grafico a TORTA per visualizzare i vari stati dei task (pending, ongoing, completed)

- Sostituisci tutte le STRINGHE nei file java con 'getString(R.string.string_name)'

- I FILTRI delle notifiche funzionano con cellulare acceso (app chiusa o aperta), ma non se il cellulare viene riavviato (i filtri vengono così azzerati).
  Soluzione: Aggiungere campo 'bool notif_on' ai task per capire se vanno notificati o meno



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
