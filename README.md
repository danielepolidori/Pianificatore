# progetto_LAM



### TODO:

- Ottenere un maggior controllo sui singoli elementi del RecyclerView.

- Ottenere a runtime la data corrente e da quello settare il nome dei giorni mostrati (partendo dal giorno corrente, quindi oltre al nome anche il numero).



### IDEE:

- Variabile universale, incrementata ad ogni creazione di un nuovo elemento del RecyclerView, per tenere traccia di quale elemento stiamo creando e così possiamo impostare l'elemento come serve (es: mesi in grassetto, giorni con il quadratino, font diversi, ...)

- RecyclerView per i giorni, CardView per i task all'interno dei giorni.

- Scheduler = Pianificatore d'attività

- Non calendario dell'app, ma possibile sincronizzazione dei task nel CALENDARIO DI SISTEMA (così che compaiano lì nella visualizzazione a griglia, mentre nell'app solo in lista).

- Mentre si scorre la home, il MESE riferito al giorno delle attività attualmente visualizzate sullo schermo rimane sempre visibile.



### ESEMPI UTILI:

- FragmentExample: bottone che ti porta in una nuova activity (per mostrare i dettagli di un task)
