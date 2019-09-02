# progetto_LAM



### TODO:

- ESTRAPOLARE dal TaskSet la visualizzazione della home (il VisSet)

- All'aggiunta di un NUOVO TASK: controllare se c'è da eliminare il msg_no_task, inserirlo in ordine cronologico, aggiorni il VisSet

- Alla RIMOZIONE di un task: se era l'unico presente aggiungere il msg_no_task nella home



### MANCA:

- Aggiunta di nuovi task

- Modifica di un task

- Inviare notifiche

- Posporre un task

- Task ongoing

- Rimozione di task svolti

- Visualizzare la home con filtri

- Grafici

- Relazione (+ slides ?)

- (Mostrare dettagli di un task)

- (Credits del progetto)



### IDEE:

- Grafico a TORTA per visualizzare le varie classi di task (famiglia, lavoro, ...)

- Variabile universale, incrementata ad ogni creazione di un nuovo elemento del RecyclerView, per tenere traccia di quale elemento stiamo creando e così possiamo impostare l'elemento come serve (es: mesi in grassetto, giorni con il quadratino, font diversi, ...)

- RecyclerView per i giorni, CardView per i task all'interno dei giorni.

- Scheduler = Pianificatore d'attività

- Non calendario dell'app, ma possibile sincronizzazione dei task nel CALENDARIO DI SISTEMA (così che compaiano lì nella visualizzazione a griglia, mentre nell'app solo in lista).

- Mentre si scorre la home, il MESE riferito al giorno delle attività attualmente visualizzate sullo schermo rimane sempre visibile.



### ESEMPI UTILI:

- FragmentExample: bottone che ti porta in una nuova activity (per mostrare i dettagli di un task)

- Slides 03 e 06 per esempi di FORM



### VERSIONE MINIMA DEL PROGETTO:

- Home con sole stringhe (le singole voci non sono cliccabili, è una home di sola lettura)

- Possibile inserimento di nuove voci

- Possibile rimozione dei task completati

- Grafico

- Relazione (+ slides ?)
