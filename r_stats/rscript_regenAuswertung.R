

data <- read.table("ausgabe.txt", sep=";")     # mit Regen
data1 <- read.table("ausgabe1.txt", sep = ";") #ohne Regen

test <- matrix(0,10,2)
test[,1] <- data$V2 #mit regen
test[,2] <- data1$V2 #ohne regen
print(test)

brk <- c(0,5,10,15,20,25,30,35,40)
brk2 <- c(0,10,20,30,40,50,60,70,80,90,100)

############Histo#############
hist(test[,1], las = 1, xlab = "Menge brennender Bäume",ylab = "Menge der Brände", main = list("Anzahl der Waldbrandereignisse"), breaks = brk, col ="blue",ylim=c(0,10))
mtext("mit Regeneinfluss, Feldgröße: 9x9")

limit <- max(test[,2])
hist(test[,2],las = 1, xlab = "Menge abgebrannter Bäume",ylab = "Menge der Brände", main = list("Anzahl der Waldbrandereignisse"), col = "red", xlim = c(0,100))
mtext("ohne Regeneinfluss, Feldgröße: 9x9")


###############Barplot###############
#barplot Menge der brennenden bäume
barplot(as.matrix(test),space = 0.1 ,beside = TRUE, main = "Brennende Bäume pro Zeitschritt", names.arg = c("1", "2","3","4","5","6","7","8","9","10"), ylab = "Menge brennender Bäume", col=heat.colors(2), xlab = "Zeitschritt", ylim= c(0,90))
mtext(side = 3 ,"Feldgröße: 9x9", cex=0.7, las = 1, par("las"))
legend("topright",c("ohne Regen","mit Regen"), cex=0.8, fill = heat.colors(2))