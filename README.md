# progetto_LAM



### TODO:

- Come si passano dati attraverso un INTENT (per passare id_task e un bool per capire se è l'id di una notifica da creare o l'id di una notifica che va cancellata)?

- Vanno resi univoci gli ID delle notifiche (usare l'id del relativo task)

- Salvare l'ID delle notifiche con Realm

- Activity per mostrare DETTAGLI task

- MODIFICA di un task (passando attraverso FormActivity e raccogliendo non tutti i dati ma solo quelli inseriti, che sono quelli nuovi)

- Aggiungere alla notifica le AZIONI postpone e ongoing



### MANCA:

- Gestione NOTIFICHE

- Mostrare dettagli di un task

- Modifica di un task

- Posporre un task

- Task ongoing

- Visualizzare la home con filtri

- Visualizzare le notifiche con filtri

- Grafici

- Relazione + slides

- (Credits del progetto)



### CONTROLLARE:

- Se si gira il cellulare in orizzontale l'app funziona ugualmente?

- Tutti i task relativi a giorni/ore passate non devono essere mostrati nella home



### IDEE:

- Grafico a TORTA per visualizzare le varie classi di task (famiglia, lavoro, ...)

- CRONOLOGIA attività completate

- Use le CARD nella home (e magari anche nelle altre activity), al posto di una semplice serie di stringhe



### BOZZE:

Test controllo mostrare NOTIFICA:

  + app sempre aperta
  + andato nella home e poi tornato nell'app
  + andato nella home
  + app chiusa e poi riaperta
  - app chiusa
  + telefono bloccato (con l'app ancora aperta)
  - telefono spento e poi app riaperta
  - telefono spento e riacceso (ma senza aprire l'app)
  - telefono spento

-------------------------------------------

Activity che mostra nel DETTAGLIO un TASK:

  - bottone Modifica
  - bottone Elimina
  - bottone Completato
