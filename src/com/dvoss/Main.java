package com.dvoss;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Main {

    static HashSet<Card> createDeck() {
        HashSet<Card> deck = new HashSet<>();
        for (Card.Suit suit : Card.Suit.values()) {  // (.values() is special enum method that returns all possibilities)
            for (Card.Rank rank : Card.Rank.values()) {
                Card c = new Card(suit, rank);
                deck.add(c);
            }
        }
        return deck;
    }

    static HashSet<HashSet<Card>> createHands(HashSet<Card> deck) {
        HashSet<HashSet<Card>> hands = new HashSet<>();
        for (Card c1 : deck) {
            HashSet<Card> deck2 = (HashSet<Card>) deck.clone();
            deck2.remove(c1);
            for (Card c2 : deck2) {
                HashSet<Card> deck3 = (HashSet<Card>) deck2.clone();
                deck3.remove(c2);
                for (Card c3 : deck3) {
                    HashSet<Card> deck4 = (HashSet<Card>) deck3.clone();
                    deck4.remove(c3);
                    for (Card c4 : deck4) {
                        HashSet<Card> hand = new HashSet<>();
                        hand.add(c1);
                        hand.add(c2);
                        hand.add(c3);
                        hand.add(c4);
                        hands.add(hand);
                    }
                }
            }
        }
        return hands;
    }

    static boolean isFlush(HashSet<Card> hand) {
        HashSet<Card.Suit> suits = hand.stream()
                .map(card -> card.suit)
                .collect(Collectors.toCollection(HashSet<Card.Suit>::new));
        return suits.size() == 1;
    }

    static boolean isFourOfAKind(HashSet<Card> hand) {
        HashSet<Card.Rank> ranks = hand.stream()
                .map(card -> card.rank)
                .collect(Collectors.toCollection(HashSet<Card.Rank>::new));
        return ranks.size() == 1;
    }

    static boolean isStraight(HashSet<Card> hand) {
        ArrayList<Integer> ranksList = hand.stream()
                .map(card -> card.rank.ordinal())
                .sorted((c1, c2) -> Integer.compare(c1.intValue(), c2.intValue()))
                .collect(Collectors.toCollection(ArrayList<Integer>::new));
        ArrayList<Integer> populatedList = new ArrayList<>();
        int rank = ranksList.get(0);
        while (populatedList.size() < ranksList.size()) {
            populatedList.add(rank);
            rank++;
        }
        return ranksList.equals(populatedList);
        // cf. clojure version
    }

    static boolean isStraightFlush(HashSet<Card> hand) {
        if (isFlush(hand) && isStraight(hand)) {
            return true;
        }
        return false;
    }

    static boolean isThreeOfAKind() {
        return false;
    }

    static boolean isTwoPair() {
        return false;
    }



    public static void main(String[] args) {
        HashSet<Card> deck = createDeck();
        HashSet<HashSet<Card>> hands = createHands(deck);

        HashSet<HashSet<Card>> flushes = hands.stream()
                .filter(Main::isFlush)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));

        HashSet<HashSet<Card>> fourOfAKind = hands.stream()
                .filter(Main::isFourOfAKind)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));

        HashSet<HashSet<Card>> straight = hands.stream()
                .filter(Main::isStraight)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));

        HashSet<HashSet<Card>> straightFlush = hands.stream()
                .filter(Main::isStraightFlush)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));

//        HashSet<HashSet<Card>> threeOfAKind = hands.stream()
//                .filter(Main::isThreeOfAKind)
//                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));
//
//        HashSet<HashSet<Card>> twoPair = hands.stream()
//                .filter(Main::isTwoPair)
//                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));

        System.out.println(flushes.size());
        System.out.println(fourOfAKind.size());
        System.out.println(straight.size());
        System.out.println(straightFlush.size());
//        System.out.println(threeOfAKind.size());
//        System.out.println(twoPair.size());
    }
}
